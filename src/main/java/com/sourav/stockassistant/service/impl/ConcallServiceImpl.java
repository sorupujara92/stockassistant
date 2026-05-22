package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.service.ConcallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ConcallServiceImpl implements ConcallService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void updateConcall(String stockName) {
        try {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        String path = "classpath:concalls/" + stockName + "/*";

        resources = resolver.getResources(path);

            String statusPath = "classpath:concalls/status/+" + stockName + ".csv";
            Resource concallFileStatusResource = resourceLoader.getResource(statusPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(concallFileStatusResource.getInputStream()));
            String line;
            String latestDate;
            while ((latestDate = reader.readLine()) != null) {
                if (latestDate.trim().isEmpty()) continue;
            }

        for (Resource resource : resources) {
            System.out.println("Reading: " + resource.getFilename());
            String fileName = resource.getFilename();
            String fileNameSplit[] = fileName.split(stockName+"_");
            if(fileNameSplit!=null && fileNameSplit.length>=2){
                String wholeDate = fileNameSplit[1];
                // get whole date with date present in status
            }

        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
