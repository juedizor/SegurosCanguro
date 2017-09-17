/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.impl;

import co.com.seguroscanguro.client.exception.ConsumeRestException;
import co.com.seguroscanguro.client.iface.ModelClientIface;
import co.com.seguroscanguro.dto.Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author jeio
 */
@Component
public class ModelClientImpl implements ModelClientIface {

    @Autowired
    Environment env;

    @Override
    public List<Model> getModels(String brand) throws ConsumeRestException {
        RestTemplate rs = new RestTemplate();
        String uri = env.getProperty("api.path") + "api/leads/choices/year/{brand}";
        Map<String, String> params = new HashMap<>();
        params.put("brand", brand);
        try {
            ResponseEntity<List<Model>> response = rs.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Model>>() {
            }, params);
            List<Model> listModel = response.getBody();
            return listModel;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ConsumeRestException("Error consumiento el servicio");
        }

    }

}
