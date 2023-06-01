package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.repository.ProductRepository;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.service.SizesService;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SizesServiceImpl implements SizesService {
    private final ProductRepository productRepository;
    private final SizesRepository sizesRepository;

    @Autowired
    public SizesServiceImpl(ProductRepository productRepository, SizesRepository sizesRepository) {
        this.productRepository = productRepository;
        this.sizesRepository = sizesRepository;
    }

    @Override
    public List<Sizes> readSizesFromCSV() throws IOException {
        List<Sizes> sizes = new ArrayList<>();
        try (Reader in = new FileReader(new ClassPathResource("csv/size.csv").getFile())) {
            CSVFormat.RFC4180.builder()
                    .setAllowMissingColumnNames(true).setHeader("id", "productId", "backSoon", "special")
                    .setSkipHeaderRecord(false).build().parse(in).forEach(line -> {
                        Sizes size = new Sizes();
                        size.setId(line.get("id"));
                        String productId = line.get("productId");
                        Product product  = productRepository.findById(productId).isPresent() ? productRepository.findById(productId).get() : null ;
                        size.setProduct(product);
                        size.setBackSoon(line.get("backSoon"));
                        size.setSpecial(line.get("special"));
                        sizes.add(size);
                    });
        }

        return sizes;
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
