package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Sizes;

import java.io.IOException;
import java.util.List;

public interface SizesService {
    List<Sizes> readSizesFromCSV() throws IOException;
    void insertSizesInBBDD(List<Sizes> sizes);

    List<Sizes> getSizesFromProductId(String productId);
}
