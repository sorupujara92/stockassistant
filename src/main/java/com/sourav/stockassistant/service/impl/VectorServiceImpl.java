package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.model.StockChunkPayload;
import com.sourav.stockassistant.service.vector.VectorService;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.CollectionHandle;

import java.io.IOException;
import java.util.*;

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
}