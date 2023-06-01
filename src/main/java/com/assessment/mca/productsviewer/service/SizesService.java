package com.assessment.mca.productsviewer.service;

import com.assessment.mca.productsviewer.model.entities.Sizes;

import java.util.List;

public interface SizesService {
    void insertSizesInBBDD(List<Sizes> sizes);

    List<Sizes> getSizesFromProductId(String productId);
}
