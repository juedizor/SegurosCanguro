/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.impl;

import co.com.seguroscanguro.client.exception.ConsumeRestException;
import co.com.seguroscanguro.client.iface.BrandClientIface;
import co.com.seguroscanguro.client.iface.CompareClientIface;
import co.com.seguroscanguro.client.iface.ModelClientIface;
import co.com.seguroscanguro.client.iface.ProxyServerIface;
import co.com.seguroscanguro.client.iface.ReferencesClientIface;
import co.com.seguroscanguro.client.response.Response;
import co.com.seguroscanguro.dto.Brand;
import co.com.seguroscanguro.dto.Compare;
import co.com.seguroscanguro.dto.Model;
import co.com.seguroscanguro.dto.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurators;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import rx.Observable;

/**
 *
 * @author julio.izquierdo
 */
@Component
public class ProxyServer implements ProxyServerIface {

    static final int DEFAULT_PORT = 8090;
    private final int port;
    @Autowired
    ModelClientIface modelClientIface;
    @Autowired
    ReferencesClientIface referencesClientIface;
    @Autowired
    BrandClientIface brandClientIface;
    @Autowired
    CompareClientIface compareClientIface;

    static final String BRAND = "/brand";
    static final String MODEL = BRAND + "/model";
    static final String REFERENCES = MODEL + "/references";
    static final String COMPARE = "/compare";

    static final String NO_PARAMETERS = "Not found parameter";

    public ProxyServer() {
        this(DEFAULT_PORT);
    }

    public ProxyServer(int port) {
        this.port = port;
        createServer().start();
    }

    @Override
    public HttpServer<ByteBuf, ByteBuf> createServer() {
        HttpServer<ByteBuf, ByteBuf> server = RxNetty.newHttpServerBuilder(port, new RequestHandler<ByteBuf, ByteBuf>() {
            @Override
            public Observable<Void> handle(HttpServerRequest<ByteBuf> request, final HttpServerResponse<ByteBuf> response) {
                String responseRest;
                try {
                    responseRest = printRequestHeader(request);
                    response.getHeaders().add(
                            "Access-Control-Allow-Origin", "*");
                    response.getHeaders().add(
                            "Access-Control-Allow-Credentials", "true");
                    response.getHeaders().add("Content-Type", "text/html; charset=UTF-8");
                    response.getHeaders().add(
                            "Access-Control-Allow-Headers",
                            "origin, content-type, accept, authorization");
                    response.getHeaders().add(
                            "Access-Control-Allow-Methods",
                            "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                    response.writeBytes(responseRest.getBytes(Charset.forName("UTF-8")));
                } catch (JsonProcessingException e) {
                    response.writeString("ERROR " + e.getMessage());
                }
                return response.close();
            }
        }).pipelineConfigurator(PipelineConfigurators.<ByteBuf, ByteBuf>httpServerConfigurator()).build();

        return server;
    }

    public String printRequestHeader(HttpServerRequest<ByteBuf> request) throws JsonProcessingException {
        String uri = request.getPath();
        if (uri.isEmpty()) {
            return getResponseError(HttpStatus.BAD_REQUEST.value(), "request bad");
        }

        Map<String, List<String>> map = request.getQueryParameters();

        if (uri.equals(BRAND)) {
            try {
                List<Brand> listBrand = brandClientIface.getBrandCar();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                String arrayToJson = objectMapper.writeValueAsString(listBrand);
                return arrayToJson;
            } catch (IOException ex) {
                return getResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            }
        }

        if (uri.equals(MODEL)) {
            /**
             *
             * get model from brand
             */
            if (map == null || map.isEmpty()) {
                return getResponseError(HttpStatus.BAD_REQUEST.value(), NO_PARAMETERS);
            }

            String brand = map.get("brand").get(0);
            try {
                List<Model> listModels = modelClientIface.getModels(brand.toLowerCase());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                String arrayToJson = objectMapper.writeValueAsString(listModels);
                return arrayToJson;
            } catch (ConsumeRestException ex) {
                return getResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            }

        }

        if (uri.equals(REFERENCES)) {
            if (map == null || map.isEmpty()) {
                return getResponseError(HttpStatus.BAD_REQUEST.value(), NO_PARAMETERS);
            }

            /**
             * get all references about cars
             */
            String brand = map.get("brand").get(0);
            String model = map.get("model").get(0);
            try {
                List<Reference> listReferences = referencesClientIface.getReferenceCar(brand.toLowerCase(), model);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                String arrayToJson = objectMapper.writeValueAsString(listReferences);
                return arrayToJson;
            } catch (ConsumeRestException ex) {
                return getResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            }

        }

        if (uri.equals(COMPARE)) {
            try {
                Compare compare = compareClientIface.getCompareSecures();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                String arrayToJson = objectMapper.writeValueAsString(compare);
                return arrayToJson;
            } catch (IOException ex) {
                return getResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            }
        }

        return "";
    }

    private String getResponseError(int statusCode, String msg) throws JsonProcessingException {
        Response response = new Response();
        response.setCodeError("" + statusCode);
        response.setMensaje(msg);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(response);
        return jsonInString;
    }

}
