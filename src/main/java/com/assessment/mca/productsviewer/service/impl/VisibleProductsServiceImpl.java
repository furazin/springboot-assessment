package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.ProductRepository;
import com.assessment.mca.productsviewer.service.SizesService;
import com.assessment.mca.productsviewer.service.StockService;
import com.assessment.mca.productsviewer.service.VisibleProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VisibleProductsServiceImpl implements VisibleProductsService {
    private final SizesService sizesService;
    private final StockService stockService;
    private final ProductRepository productRepository;

    @Autowired
    public VisibleProductsServiceImpl(SizesService sizesService, StockService stockService, ProductRepository productRepository) {
        this.sizesService = sizesService;
        this.stockService = stockService;
        this.productRepository = productRepository;
    }

    @Override
    public List<String> getVisibleProducts() {
        List<Product> productsFromBD = productRepository.findAll();
        final List<Product> visibleProducts = getProductsWithStock(productsFromBD);
        sortProductsBySequence(visibleProducts);

        return sortedProductsIds(visibleProducts);
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
            if (hasStock(stocksFromSize) || isBackSoonOrSpecialSize(size, stocksFromSize)) {
                return true;
            }
        }

        return false;
    }

    private boolean isBackSoonOrSpecialSize(Sizes size, List<Stock> stocksFromSize) {
        return !CollectionUtils.isEmpty(stocksFromSize) && (isBackSoon(size) || hasSpecialSize(size));
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
