package com.sourav.stockassistant.service;

import java.io.IOException;

public interface ScreenerData {
    String getFinancials(String stockName) throws IOException;
}
