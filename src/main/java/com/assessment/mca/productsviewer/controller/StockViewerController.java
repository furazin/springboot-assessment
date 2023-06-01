package com.assessment.mca.productsviewer.controller;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.service.ProductService;
import com.assessment.mca.productsviewer.service.SizesService;
import com.assessment.mca.productsviewer.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/api/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockViewerController {

    private final ProductService productService;
    private final SizesService sizesService;
    private final StockService stockService;

    @Autowired
    public StockViewerController(ProductService productService, SizesService sizesService, StockService stockService) {
        this.productService = productService;
        this.sizesService = sizesService;
        this.stockService = stockService;
    }

    @GetMapping("/view")
    public List<String> view() {
        List<Product> productsFromCSV = getProductsFromCSV();
        insertProductsInBBDD(productsFromCSV);

        List<Sizes> sizesFromCSV = getSizesFromCSV();
        insertSizesInBBDD(sizesFromCSV);

        List<Stock> stocksFromCSV = getStocksFromCSV();
        insertStocksInBBDD(stocksFromCSV);

        final List<Product> visibleProducts = getProductsWithStock(productsFromCSV);
        sortProductsBySequence(visibleProducts);

        return visibleProducts.stream().map(Product::getId).toList();
    }

    private List<Product> getProductsFromCSV() {
        List<Product> productsFromCSV;
        try {
            productsFromCSV = productService.readProductsFromCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productsFromCSV;
    }

    private void insertProductsInBBDD(List<Product> productsFromCSV) {
        productService.insertProductsInBBDD(productsFromCSV);
    }

    private List<Sizes> getSizesFromCSV() {
        List<Sizes> sizesFromCSV;
        try {
            sizesFromCSV = sizesService.readSizesFromCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sizesFromCSV;
    }

    private void insertSizesInBBDD(List<Sizes> sizesFromCSV) {
        sizesService.insertSizesInBBDD(sizesFromCSV);
    }

    private List<Stock> getStocksFromCSV() {
        List<Stock> stocksFromCSV;
        try {
            stocksFromCSV = stockService.readStocksFromCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stocksFromCSV;
    }

    private void insertStocksInBBDD(List<Stock> stocksFromCSV) {
        stockService.insertStocks(stocksFromCSV);
    }

    private List<Product> getProductsWithStock(List<Product> products) {
        final List<Product> visibleProducts = new ArrayList<>();
        for (Product product : products) {
            boolean isVisibleProduct = false;
            List<Sizes> sizes = sizesService.getSizesFromProductId(product.getId());
            if (checkProductStock(sizes)) {
                isVisibleProduct = true;
            }
            else {
                boolean hasProductBackSoonSizes = checkBackSoonSizes(sizes);
                boolean hasProductSpecialSizes = checkSpecialSizes(sizes);
                if (hasProductBackSoonSizes || hasProductSpecialSizes) {
                    isVisibleProduct = true;
                }
            }

            if (isVisibleProduct) {
                visibleProducts.add(product);
            }
        }

        return visibleProducts;
    }

    private boolean checkProductStock(List<Sizes> sizes) {
        for (Sizes size : sizes) {
            List<Stock> stocksFromSize = stockService.getStocksFromSizeId(size.getId());
            for (Stock stock : stocksFromSize) {
                if (Integer.parseInt(stock.getQuantity()) > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkBackSoonSizes(List<Sizes> sizes) {
        for (Sizes size : sizes) {
            List<Stock> stocksFromSize = stockService.getStocksFromSizeId(size.getId());
            if (!CollectionUtils.isEmpty(stocksFromSize) && isBackSoon(size)) {
                return true;
            }
        }

        return false;
    }
    private boolean isBackSoon(Sizes size) {
        return Boolean.parseBoolean(size.getBackSoon());
    }

    private boolean checkSpecialSizes(List<Sizes> sizes) {
        for (Sizes size : sizes) {
            List<Stock> stocksFromSize = stockService.getStocksFromSizeId(size.getId());
            if (!CollectionUtils.isEmpty(stocksFromSize) && hasSpecialSize(size)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasSpecialSize(Sizes size) {
        boolean hasSpecialSizeWithStock = false;
            if (isSpecial(size)) {
                hasSpecialSizeWithStock = true;
            }

        return hasSpecialSizeWithStock;
    }

    private static boolean isSpecial(Sizes size) {
        return Boolean.parseBoolean(size.getSpecial());
    }

    private static void sortProductsBySequence(List<Product> visibleProducts) {
        visibleProducts.sort(Comparator.comparingInt((Product p) -> Integer.parseInt(p.getSeq())));
    }
}