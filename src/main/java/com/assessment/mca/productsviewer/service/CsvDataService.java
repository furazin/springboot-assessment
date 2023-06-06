package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Product;

import java.io.IOException;
import java.util.List;

public interface CsvDataService {
    List<Product> readAndPersistData() throws IOException;
}
