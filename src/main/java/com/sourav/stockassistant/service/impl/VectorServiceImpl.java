package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.model.StockChunkPayload;
import com.sourav.stockassistant.service.vector.VectorService;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.CollectionHandle;
import io.weaviate.client6.v1.api.collections.query.Bm25;
import io.weaviate.client6.v1.api.collections.query.FetchObjects;
import io.weaviate.client6.v1.api.collections.query.Filter;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.*;

@Service
public class VectorServiceImpl implements VectorService {

    @Override
    public void ingestStockChunksBatch(WeaviateClient client, List<StockChunkPayload> chunks) throws IOException {

        CollectionHandle<Map<String, Object>> collection =
                client.collections.use("StockDocument");


        for (StockChunkPayload data : chunks) {

            Map<String, Object> props = new HashMap<>();
            props.put("stockName", data.getStockName());
            props.put("fileName", data.getFileName());
            props.put("content", data.getContent());

            collection.data.insert(props);
        }

    }


   @Override
   public String retrieveStockContext(
           WeaviateClient client,
           String stock,
           String question) {

        CollectionHandle<Map<String, Object>> collection =
                client.collections.use("StockDocument");
        var response = collection.query.fetchObjects(
                FetchObjects.of(f -> f
                        .limit(5)
                        .filters(
                                Filter.property("stockName")
                                        .eq(stock)
                        )
                )
        );

        StringBuilder context = new StringBuilder();

        response.objects().forEach(obj -> {

            Map<String, Object> props = obj.properties();

            context.append(props.get("content"))
                    .append("\n\n");
        });

        return context.toString();
    }

    @Override
    public String askQuestion(
            WeaviateClient client,
            String stock,
            String question) {

        CollectionHandle<Map<String, Object>> collection =
                client.collections.use("StockDocument");

        Bm25 bm25 = Bm25.of(
                question,
                b -> b
                        .limit(5)
                        .filters(
                                Filter.property("stockName")
                                        .eq(stock)
                        )
        );

        var response = collection.query.bm25(bm25);

        StringBuilder context = new StringBuilder();

        response.objects().forEach(obj -> {

            Map<String, Object> props = obj.properties();

            context.append(props.get("content"))
                    .append("\n\n");
        });

        return context.toString();
    }
}