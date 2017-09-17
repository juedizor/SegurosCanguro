/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.impl;

import co.com.seguroscanguro.client.exception.ConsumeRestException;
import co.com.seguroscanguro.client.iface.ReferencesClientIface;
import co.com.seguroscanguro.dto.Model;
import co.com.seguroscanguro.dto.Reference;
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
public class ReferenceClientImpl implements ReferencesClientIface {

    @Autowired
    Environment env;

    @Override
    public List<Reference> getReferenceCar(String brand, String model) throws ConsumeRestException {
        RestTemplate rs = new RestTemplate();
        String uri = env.getProperty("api.path") + "/api/leads/choices/model/{brand}/{model}";
        Map<String, String> params = new HashMap<>();
        params.put("brand", brand);
        params.put("model", model);
        try {
            ResponseEntity<List<Reference>> response = rs.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Reference>>() {
            }, params);
            List<Reference> listReferences = response.getBody();
            return listReferences;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ConsumeRestException("Error consumiento el servicio");
        }
    }

}
