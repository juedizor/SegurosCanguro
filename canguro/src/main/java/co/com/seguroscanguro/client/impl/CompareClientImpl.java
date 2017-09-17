/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.seguroscanguro.client.impl;

import co.com.seguroscanguro.client.iface.CompareClientIface;
import co.com.seguroscanguro.dto.Brand;
import co.com.seguroscanguro.dto.Compare;
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
public class CompareClientImpl implements CompareClientIface {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public Compare getCompareSecures() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:compare.json");
        InputStream dbAsStream = resource.getInputStream();
        String pathTmp = System.getProperty("java.io.tmpdir") + File.separatorChar + "compare.json";
        File pathFile = new File(pathTmp);
        pathFile.createNewFile();
        FileUtils.copyInputStreamToFile(dbAsStream, pathFile);
        String value = FileUtils.readFileToString(pathFile, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Compare compare = objectMapper.readValue(value, Compare.class);
        pathFile.delete();
        return compare;
    }

}
