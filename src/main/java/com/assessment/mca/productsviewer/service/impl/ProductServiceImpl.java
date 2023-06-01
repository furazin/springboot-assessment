package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.repository.ProductRepository;
import com.assessment.mca.productsviewer.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> readProductsFromCSV() throws IOException {
        List<Product> products = new ArrayList<>();
        try (Reader in = new FileReader(new ClassPathResource("csv/product.csv").getFile())) {
            CSVFormat.RFC4180.builder()
                    .setAllowMissingColumnNames(true).setHeader("id", "Seq").build().parse(in).forEach(line -> {
                        Product product = new Product();
                        product.setId(line.get("id"));
                        product.setSeq(line.get("Seq"));
                        products.add(product);
                    });
        }

        return products;
    }

    @Override
    public void insertProductsInBBDD(List<Product> products) {
        productRepository.saveAll(products);
    }
}
