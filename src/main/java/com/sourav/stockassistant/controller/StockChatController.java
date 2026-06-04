package com.sourav.stockassistant.controller;

import com.sourav.stockassistant.request.AnalyzeStocks;
import com.sourav.stockassistant.service.StockService;
import io.weaviate.client6.v1.api.WeaviateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class StockChatController {

    @Autowired
    StockService stockService;

    @Autowired
    WeaviateClient weaviateClient;

    @PostMapping("/analyze")
    ResponseEntity<String> getStockData(@RequestBody AnalyzeStocks concallRequest) {

        String response = stockService.analyzeStock(weaviateClient,concallRequest.getStock());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/ask")
    ResponseEntity<String> askQuestion(@RequestBody AnalyzeStocks analyzeStocks) {

        String response = stockService.askQuestion(weaviateClient,analyzeStocks.getStock(),analyzeStocks.getQuestion());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
