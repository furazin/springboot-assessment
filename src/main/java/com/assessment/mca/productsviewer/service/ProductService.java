package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Product;

import java.util.List;

public interface ProductService {
    public void insertProductsInBBDD(List<Product> products);
}
