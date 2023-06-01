package com.assessment.mca.productsviewer.csv.impl;

import com.assessment.mca.productsviewer.csv.impl.CsvServiceImpl;
import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.ProductRepository;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.csv.CsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CsvServiceImplTest {

    private CsvService csvService;
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final SizesRepository sizesRepository = mock(SizesRepository.class);

    @BeforeEach
    void setUp() {
        csvService = new CsvServiceImpl(productRepository, sizesRepository);
    }

    @Test
    void shouldReadProductsFromCSV() throws IOException {
        // given
        File file = new ClassPathResource("csv/stock.csv").getFile();

        // when
        List<Product> result = csvService.readProductsFromCSV(file);

        // then
        assertNotNull(result);
    }

    @Test
    void shouldReadSizesFromCSV() throws IOException {
        // given
        File file = new ClassPathResource("csv/size.csv").getFile();

        // when
        List<Sizes> result = csvService.readSizesFromCSV(file);

        // then
        assertNotNull(result);
    }

    @Test
    void shouldReadStocksFromCSV() throws IOException {
        // given
        File file = new ClassPathResource("csv/stock.csv").getFile();

        // when
        List<Stock> result = csvService.readStocksFromCSV(file);

        // then
        assertNotNull(result);
    }
}