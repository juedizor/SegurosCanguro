/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurators;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import rx.Observable;

/**
 *
 * @author julio.izquierdo
 */
@Component
public class ProxyServer {

    static final int DEFAULT_PORT = 8090;

    private final int port;

    public ProxyServer() {
        this(DEFAULT_PORT);
    }

    public ProxyServer(int port) {
        this.port = port;
        createServer().startAndWait();
    }

    public HttpServer<ByteBuf, ByteBuf> createServer() {
        HttpServer<ByteBuf, ByteBuf> server = RxNetty.newHttpServerBuilder(port, new RequestHandler<ByteBuf, ByteBuf>() {
            @Override
            public Observable<Void> handle(HttpServerRequest<ByteBuf> request, final HttpServerResponse<ByteBuf> response) {
                try {
                    String value = printRequestHeader(request);
                    /**
                     * peticiones a los servicios segun la
                     */
                    response.writeString(value);
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                return response.close();
            }
        }).pipelineConfigurator(PipelineConfigurators.<ByteBuf, ByteBuf>httpServerConfigurator()).build();

        System.out.println("HTTP hello world server started...");
        return server;
    }

    public String printRequestHeader(HttpServerRequest<ByteBuf> request) throws JsonProcessingException {
        System.out.println("New request received");
        request.getQueryParameters();
        request.getPath();
        System.out.println(request.getHttpMethod() + " " + request.getUri() + ' ' + request.getHttpVersion());
        for (Map.Entry<String, String> header : request.getHeaders().entries()) {
            System.out.println(header.getKey() + ": " + header.getValue());
        }
        DtoPrueba dto = new DtoPrueba();
        dto.setDato("prueba");
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(dto);
        return jsonInString;

    }
}
