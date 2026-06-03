package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.service.ScreenerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class ScreenerDataImpl implements ScreenerData {

    @Autowired
    ResourceLoader resourceLoader;
    @Override
    public String getFinancials(String stockName) throws IOException {
        String financialData = "classpath:screener_downloads/" + stockName + ".json";
        Resource resource = resourceLoader.getResource(financialData);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
