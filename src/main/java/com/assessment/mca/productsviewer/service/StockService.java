package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Stock;

import java.util.List;

public interface StockService {
    void insertStocks(List<Stock> stocks);
    List<Stock> getStocksFromSizeId(String sizeId);
}
