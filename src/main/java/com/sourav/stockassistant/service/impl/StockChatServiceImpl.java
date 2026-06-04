package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.service.LLMService;
import com.sourav.stockassistant.service.StockService;
import com.sourav.stockassistant.service.vector.VectorService;
import io.weaviate.client6.v1.api.WeaviateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockChatServiceImpl implements StockService {


    @Autowired
    private VectorService vectorService;

    @Autowired
    private LLMService llmService;

    @Override
    public String askQuestion(
            WeaviateClient client,
            String stock,
            String question) {

        String context =
                vectorService.askQuestion(
                        client,
                        stock,
                        question
                );

        String prompt = """
                Answer ONLY using the supplied context.
                
                If information is not available,
                say:
                
                Not found in concall data.
                
                Context:
                
                %s
                
                Question:
                
                %s
                """.formatted(
                context,
                question
        );

        return llmService.ask(prompt);
    }

    @Override
    public String analyzeStock(
            WeaviateClient client,
            String stock) {

        String context =
                vectorService.retrieveStockContext(
                        client,
                        stock
                );

        String prompt = """
                You are an expert stock analyst.
                
                Analyze the company using
                financial data and concall commentary.
                
                Provide:
                
                1. Overall score out of 10
                2. Positives
                3. Negatives
                4. Risks
                5. Management quality
                6. Growth outlook
                7. Final verdict
                
                Context:
                
                %s
                """.formatted(context);

        return llmService.ask(prompt);
    }

}

