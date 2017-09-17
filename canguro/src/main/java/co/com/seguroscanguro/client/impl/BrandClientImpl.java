/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.impl;

import co.com.seguroscanguro.client.iface.BrandClientIface;
import co.com.seguroscanguro.dto.Brand;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 *
 * @author jeio
 */
@Component
public class BrandClientImpl implements BrandClientIface {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public List<Brand> getBrandCar() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:brand.json");
        InputStream dbAsStream = resource.getInputStream();
        String pathTmp = System.getProperty("java.io.tmpdir") + File.separatorChar + "brand.json";
        File pathFile = new File(pathTmp);
        pathFile.createNewFile();
        FileUtils.copyInputStreamToFile(dbAsStream, pathFile);
        String value = FileUtils.readFileToString(pathFile, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        TypeReference<List<Brand>> mapType = new TypeReference<List<Brand>>() {
        };
        List<Brand> jsonToPersonList = objectMapper.readValue(value, mapType);
        pathFile.delete();
        return jsonToPersonList;
    }

}
