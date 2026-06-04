package com.sourav.stockassistant.service.vector;

import com.sourav.stockassistant.model.StockChunkPayload;
import io.weaviate.client6.v1.api.WeaviateClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface VectorService {


    void ingestStockChunksBatch(WeaviateClient client, List<StockChunkPayload> chunks) throws IOException;

    String retrieveStockContext(
            WeaviateClient client,
            String stock);

    String askQuestion(
            WeaviateClient client,
            String stock,
            String question);
}
