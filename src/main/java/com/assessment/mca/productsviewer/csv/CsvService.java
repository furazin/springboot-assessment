package com.assessment.mca.productsviewer.csv;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CsvService {
    List<Product> readProductsFromCSV(File file) throws IOException;
    List<Sizes> readSizesFromCSV(File file) throws IOException;
    List<Stock> readStocksFromCSV(File file) throws IOException;
}
