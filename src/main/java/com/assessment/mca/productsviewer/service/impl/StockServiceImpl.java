package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.StockRepository;
import com.assessment.mca.productsviewer.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void insertStocks(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }

    @Override
    public List<Stock> getStocksFromSizeId(String sizeId) {
        return stockRepository.findStocksBySizesId(sizeId);
    }
}
