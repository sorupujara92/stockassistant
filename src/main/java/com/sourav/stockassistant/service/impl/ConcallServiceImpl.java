package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.model.StockChunkPayload;
import com.sourav.stockassistant.service.ConcallService;
import com.sourav.stockassistant.service.PdfChunkService;
import com.sourav.stockassistant.service.ScreenerData;
import com.sourav.stockassistant.service.vector.VectorService;
import io.weaviate.client6.v1.api.WeaviateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ConcallServiceImpl implements ConcallService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    VectorService vectorService;

    @Autowired
    PdfChunkService pdfChunkService;

    @Autowired
    WeaviateClient weaviateClient;

    @Autowired
    ScreenerData screenerData;
    @Override
    public void updateConcall(String stockName) {
        try {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        String path = "classpath:concalls/" + stockName + "/*";

        resources = resolver.getResources(path);

            String statusPath = "classpath:concalls/status/" + stockName + ".csv";
            Resource concallFileStatusResource = resourceLoader.getResource(statusPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(concallFileStatusResource.getInputStream()));
            String line;
            String latestDate = null;
            while ((reader.readLine()) != null) {
                latestDate = reader.readLine();
            }

        for (Resource resource : resources) {
            String fileName = resource.getFilename();

            String fileNameSplit[] = fileName.split(stockName.toUpperCase(Locale.ROOT)+"_");
            if(fileNameSplit.length>=2){
                String wholeDate = fileNameSplit[1].split("_")[0];
                if(latestDate!=null && Integer.parseInt(latestDate)>Integer.parseInt(wholeDate)){
                    continue;
                }
                List<String> chunks = pdfChunkService.extractSemanticChunks(resource.getFilePath().toString());
                List<StockChunkPayload> stockChunkPayloads = new ArrayList<>();
                String financials = screenerData.getFinancials(stockName);
                chunks.stream().forEach(s -> {
                    StockChunkPayload stockChunkPayload = new StockChunkPayload();
                    stockChunkPayload.setContent(s);
                    stockChunkPayload.setStockName(stockName);
                    stockChunkPayload.setFileName(fileName);
                    stockChunkPayload.setFinancials(financials);
                    stockChunkPayloads.add(stockChunkPayload);
                });
                vectorService.ingestStockChunksBatch(weaviateClient,stockChunkPayloads);
            }

        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
