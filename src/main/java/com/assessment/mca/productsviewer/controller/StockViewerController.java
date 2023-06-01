package com.assessment.mca.productsviewer.controller;

import com.assessment.mca.productsviewer.csv.CsvService;
import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.service.ProductService;
import com.assessment.mca.productsviewer.service.SizesService;
import com.assessment.mca.productsviewer.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/api/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockViewerController {

    private final CsvService csvService;
    private final ProductService productService;
    private final SizesService sizesService;
    private final StockService stockService;

    @Autowired
    public StockViewerController(CsvService csvService, ProductService productService, SizesService sizesService, StockService stockService) {
        this.csvService = csvService;
        this.productService = productService;
        this.sizesService = sizesService;
        this.stockService = stockService;
    }

    @GetMapping("/view")
    public List<String> view() {
        try {
            List<Product> productsFromCSV = getProductsFromCSV();
            insertProductsInBBDD(productsFromCSV);

            List<Sizes> sizesFromCSV = getSizesFromCSV();
            insertSizesInBBDD(sizesFromCSV);

            List<Stock> stocksFromCSV = getStocksFromCSV();
            insertStocksInBBDD(stocksFromCSV);

            final List<Product> visibleProducts = getProductsWithStock(productsFromCSV);
            sortProductsBySequence(visibleProducts);

            return sortedProductsIds(visibleProducts);
        } catch(Exception exception) {
            throw new RuntimeException(exception);
        }
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

    private List<Product> getProductsWithStock(List<Product> products) {
        final List<Product> visibleProducts = new ArrayList<>();
        for (Product product : products) {
            List<Sizes> sizes = sizesService.getSizesFromProductId(product.getId());
            boolean isVisibleProduct = isVisibleProduct(sizes);
            if (isVisibleProduct) {
                visibleProducts.add(product);
            }
        }

        return visibleProducts;
    }

    private boolean isVisibleProduct(List<Sizes> sizes) {
        for (Sizes size : sizes) {
            List<Stock> stocksFromSize = stockService.getStocksFromSizeId(size.getId());
            if (hasStock(stocksFromSize)) {
                return true;
            }
            if (!CollectionUtils.isEmpty(stocksFromSize) && (isBackSoon(size) || hasSpecialSize(size))) {
                return true;
            }
        }
        
        return false;
    }

    private static boolean hasStock(List<Stock> stocksFromSize) {
        for (Stock stock : stocksFromSize) {
            if (Integer.parseInt(stock.getQuantity()) > 0) {
                return true;
            }
        }
        return false;
    }
    private boolean isBackSoon(Sizes size) {
        return Boolean.parseBoolean(size.getBackSoon());
    }

    private boolean hasSpecialSize(Sizes size) {
        return Boolean.parseBoolean(size.getSpecial());
    }

    private void sortProductsBySequence(List<Product> visibleProducts) {
        visibleProducts.sort(Comparator.comparingInt((Product p) -> Integer.parseInt(p.getSeq())));
    }

    private List<String> sortedProductsIds(List<Product> visibleProducts) {
        return visibleProducts.stream().map(Product::getId).toList();
    }
}