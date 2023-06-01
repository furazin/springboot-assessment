package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.service.SizesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SizesServiceImpl implements SizesService {
    private final SizesRepository sizesRepository;

    @Autowired
    public SizesServiceImpl(SizesRepository sizesRepository) {
        try {
            this.sizesRepository = sizesRepository;
        } catch (Exception e) {
            log.error("error in saving sizes in database");
            throw e;
        }
    }

    @Override
    public void insertSizesInBBDD(List<Sizes> sizes) {
        sizesRepository.saveAll(sizes);
    }

    @Override
    public List<Sizes> getSizesFromProductId(String productId) {
        try {
            return sizesRepository.findSizesByProductId(productId);
        } catch (Exception e) {
            log.error("error in finding sizes by productId");
            throw e;
        }
    }
}
