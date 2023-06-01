package com.assessment.mca.productsviewer.service.csv.impl;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.ProductRepository;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.service.csv.CsvService;
import org.apache.commons.csv.CSVFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {
    private final ProductRepository productRepository;
    private final SizesRepository sizesRepository;

    public CsvServiceImpl(ProductRepository productRepository, SizesRepository sizesRepository) {
        this.productRepository = productRepository;
        this.sizesRepository = sizesRepository;
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
    public List<Stock> readStocksFromCSV() throws IOException {
        List<Stock> stocks = new ArrayList<>();
        try (Reader in = new FileReader(new ClassPathResource("csv/stock.csv").getFile())) {
            CSVFormat.RFC4180.builder()
                    .setAllowMissingColumnNames(true).setHeader("sizeId", "quantity")
                    .setSkipHeaderRecord(false).build().parse(in).forEach(line -> {
                        Stock stock = new Stock();
                        String sizeId = line.get("sizeId");
                        Sizes size = sizesRepository.findById(sizeId).isPresent() ? sizesRepository.findById(sizeId).get() : null;
                        stock.setSize(size);
                        stock.setQuantity(line.get("quantity"));
                        stocks.add(stock);
                    });
        }

        return stocks;
    }
}
