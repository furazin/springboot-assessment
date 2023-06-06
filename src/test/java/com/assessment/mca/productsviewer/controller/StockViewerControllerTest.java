package com.assessment.mca.productsviewer.controller;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.service.CsvDataService;
import com.assessment.mca.productsviewer.service.VisibleProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StockViewerControllerTest {

    private final CsvDataService csvDataService = mock(CsvDataService.class);
    private final VisibleProductsService visibleProductsService = mock(VisibleProductsService.class);
    private StockViewerController stockViewerController;

    @BeforeEach
    void setUp() {
        stockViewerController = new StockViewerController(csvDataService, visibleProductsService);
    }

    @Test
    void shouldThrowErrorWhenReadingProductsFromCSV(){
        // given
        RuntimeException ioException = mock(RuntimeException.class);
        when(ioException.getMessage()).thenReturn("error reading");

        // when
        try {
            stockViewerController.view();
        } catch(Exception e) {
            assertInstanceOf(RuntimeException.class, e);
        }
    }

    @Test
    void shouldView() throws IOException {
        // given
        List<Product> products = buildProducts();
        when(csvDataService.readAndPersistData()).thenReturn(products);

        // when
        List<String> result = stockViewerController.view();

        // then
        assertNotNull(result);
        verify(csvDataService).readAndPersistData();
        verify(visibleProductsService).getVisibleProducts(anyList());
    }

    private static List<Product> buildProducts() {
        Product product1 = mock(Product.class);
        when(product1.getId()).thenReturn("id1");
        when(product1.getSeq()).thenReturn("1");
        Product product2 = mock(Product.class);
        when(product2.getId()).thenReturn("id2");
        when(product2.getSeq()).thenReturn("2");
        List<Product> products = Arrays.asList(product1, product2);
        return products;
    }
}