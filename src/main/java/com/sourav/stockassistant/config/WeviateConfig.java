package com.sourav.stockassistant.config;

import io.weaviate.client6.v1.api.WeaviateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeviateConfig {

    @Value("${weaviate.host}")
    private String weaviateHost;

    @Value("${weaviate.api-key}")
    private String weaviateApiKey;


    @Bean
    public WeaviateClient weaviateClient() {

        return WeaviateClient.connectToWeaviateCloud(
                weaviateHost,
                weaviateApiKey
        );
    }
}