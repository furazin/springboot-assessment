package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Product;

import java.util.List;

public interface VisibleProductsService {
    List<String> getVisibleProducts(List<Product> products);
}
