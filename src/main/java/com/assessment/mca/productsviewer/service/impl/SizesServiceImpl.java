package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.service.SizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizesServiceImpl implements SizesService {
    private final SizesRepository sizesRepository;

    @Autowired
    public SizesServiceImpl(SizesRepository sizesRepository) {
        this.sizesRepository = sizesRepository;
    }

    @Override
    public void insertSizesInBBDD(List<Sizes> sizes) {
        sizesRepository.saveAll(sizes);
    }

    @Override
    public List<Sizes> getSizesFromProductId(String productId) {
        return sizesRepository.findSizesByProductId(productId);
    }
}
