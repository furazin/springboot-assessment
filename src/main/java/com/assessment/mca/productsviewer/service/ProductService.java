package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public List<Product> readProductsFromCSV() throws IOException;
    public void insertProductsInBBDD(List<Product> products);
}
