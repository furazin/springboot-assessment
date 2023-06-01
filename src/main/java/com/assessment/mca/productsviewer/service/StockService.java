package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Stock;

import java.io.IOException;
import java.util.List;

public interface StockService {
    List<Stock> readStocksFromCSV() throws IOException;
    void insertStocks(List<Stock> stocks);
    List<Stock> getStocksFromSizeId(String sizeId);
}
