package com.sourav.stockassistant.model;

public class StockChunkPayload {
    private String stockName;
    private String content;
    private String fileName;
    private String financials;

    public String getFinancials() {
        return financials;
    }

    public void setFinancials(String financials) {
        this.financials = financials;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
