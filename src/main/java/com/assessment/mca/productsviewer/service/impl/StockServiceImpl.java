package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.StockRepository;
import com.assessment.mca.productsviewer.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void insertStocksInBBDD(List<Stock> stocks) {
        try {
            stockRepository.saveAll(stocks);
        } catch (Exception e) {
            log.error("error in saving stocks in database");
            throw e;
        }
    }

    @Override
    public List<Stock> getStocksFromSizeId(String sizeId) {
        try {
        return stockRepository.findStocksBySizesId(sizeId);
    } catch (Exception e) {
        log.error("error in finding stocks by sizesId");
        throw e;
    }
    }
}
