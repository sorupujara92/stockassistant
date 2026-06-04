package com.sourav.stockassistant.service;


import io.weaviate.client6.v1.api.WeaviateClient;

public interface StockService {
    String askQuestion(
            WeaviateClient client,
            String stock,
            String question);

    String analyzeStock(
            WeaviateClient client,
            String stock);
}
