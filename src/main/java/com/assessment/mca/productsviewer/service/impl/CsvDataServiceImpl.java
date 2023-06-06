package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.csv.CsvService;
import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.service.ProductService;
import com.assessment.mca.productsviewer.service.CsvDataService;
import com.assessment.mca.productsviewer.service.SizesService;
import com.assessment.mca.productsviewer.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CsvDataServiceImpl implements CsvDataService {
    private final CsvService csvService;
    private final ProductService productService;
    private final SizesService sizesService;
    private final StockService stockService;

    @Autowired
    public CsvDataServiceImpl(CsvService csvService, ProductService productService, SizesService sizesService, StockService stockService) {
        this.csvService = csvService;
        this.productService = productService;
        this.sizesService = sizesService;
        this.stockService = stockService;
    }

    @Override
    public List<Product> readAndPersistData() throws IOException {
        List<Product> productsFromCSV = getProductsFromCSV();
        insertProductsInBBDD(productsFromCSV);

        List<Sizes> sizesFromCSV = getSizesFromCSV();
        insertSizesInBBDD(sizesFromCSV);

        List<Stock> stocksFromCSV = getStocksFromCSV();
        insertStocksInBBDD(stocksFromCSV);
        return productsFromCSV;
    }

    private List<Product> getProductsFromCSV() throws IOException {
        List<Product> productsFromCSV;
        File file = new ClassPathResource("csv/product.csv").getFile();
        productsFromCSV = csvService.readProductsFromCSV(file);

        return productsFromCSV;
    }

    private void insertProductsInBBDD(List<Product> productsFromCSV) {
        productService.insertProductsInBBDD(productsFromCSV);
    }

    private List<Sizes> getSizesFromCSV() throws IOException {
        List<Sizes> sizesFromCSV;
        File file = new ClassPathResource("csv/size.csv").getFile();
        sizesFromCSV = csvService.readSizesFromCSV(file);

        return sizesFromCSV;
    }

    private void insertSizesInBBDD(List<Sizes> sizesFromCSV) {
        sizesService.insertSizesInBBDD(sizesFromCSV);
    }

    private List<Stock> getStocksFromCSV() throws IOException {
        List<Stock> stocksFromCSV;

        File file = new ClassPathResource("csv/stock.csv").getFile();
        stocksFromCSV = csvService.readStocksFromCSV(file);

        return stocksFromCSV;
    }

    private void insertStocksInBBDD(List<Stock> stocksFromCSV) {
        stockService.insertStocksInBBDD(stocksFromCSV);
    }
}
