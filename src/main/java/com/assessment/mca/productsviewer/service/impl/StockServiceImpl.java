package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.model.repository.StockRepository;
import com.assessment.mca.productsviewer.service.StockService;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final SizesRepository sizesRepository;
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(SizesRepository sizesRepository, StockRepository stockRepository) {
        this.sizesRepository = sizesRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> readStocksFromCSV() throws IOException {
        List<Stock> stocks = new ArrayList<>();
        try (Reader in = new FileReader(new ClassPathResource("csv/stock.csv").getFile())) {
            CSVFormat.RFC4180.builder()
                    .setAllowMissingColumnNames(true).setHeader("sizeId", "quantity")
                    .setSkipHeaderRecord(false).build().parse(in).forEach(line -> {
                        Stock stock = new Stock();
                        String sizeId = line.get("sizeId");
                        Sizes size = sizesRepository.findById(sizeId).isPresent() ? sizesRepository.findById(sizeId).get() : null;
                        stock.setSize(size);
                        stock.setQuantity(line.get("quantity"));
                        stocks.add(stock);
                    });
        }

        return stocks;
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
