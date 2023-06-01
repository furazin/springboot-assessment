package com.assessment.mca.productsviewer.service.csv;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;

import java.io.IOException;
import java.util.List;

public interface CsvService {
    List<Product> readProductsFromCSV() throws IOException;
    List<Sizes> readSizesFromCSV() throws IOException;
    List<Stock> readStocksFromCSV() throws IOException;
}
