package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.csv.CsvService;
import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.service.ProductService;
import com.assessment.mca.productsviewer.service.SizesService;
import com.assessment.mca.productsviewer.service.StockService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CsvDataServiceImplTest {

    private final CsvService csvService = mock(CsvService.class);
    private final ProductService productService = mock(ProductService.class);
    private final SizesService sizesService = mock(SizesService.class);
    private final StockService stockService = mock(StockService.class);

    private final CsvDataServiceImpl csvDataService = new CsvDataServiceImpl(csvService, productService, sizesService,stockService );

    @Test
    void shouldThrowErrorWhenReadingProductsFromCSV() throws IOException {
        // given
        RuntimeException ioException = mock(RuntimeException.class);
        when(ioException.getMessage()).thenReturn("error reading");
        when(csvService.readProductsFromCSV(any(File.class))).thenThrow(ioException);

        // when
        try {
            csvDataService.readAndPersistData();
        } catch(Exception e) {
            assertInstanceOf(RuntimeException.class, e);
        }
    }

    @Test
    void shouldReadDatFromCsv() throws IOException {
        // given
        List<Product> products = buildProducts();
        when(csvService.readProductsFromCSV(any(File.class))).thenReturn(products);
        List<Sizes> sizes = buildSizes(false, false);
        when(csvService.readSizesFromCSV(any(File.class))).thenReturn(sizes);
        List<Stock> stocks = buildStocks(true);
        when(csvService.readStocksFromCSV(any(File.class))).thenReturn(stocks);

        // when
        csvDataService.readAndPersistData();

        // then
        verify(sizesService).insertSizesInBBDD(anyList());
        verify(stockService).insertStocksInBBDD(anyList());
        csvService.readProductsFromCSV(any(File.class));
    }

    private static List<Stock> buildStocks(boolean hasQuantity) {
        Stock stock1 = mock(Stock.class);
        Stock stock2 = mock(Stock.class);
        if (hasQuantity) {
            when(stock1.getQuantity()).thenReturn("10");
            when(stock2.getQuantity()).thenReturn("20");
        } else {
            when(stock1.getQuantity()).thenReturn("0");
            when(stock2.getQuantity()).thenReturn("0");
        }
        List<Stock> stocks = Arrays.asList(stock1,stock2);
        return stocks;
    }

    private static List<Sizes> buildSizes(boolean hasBackSoon, boolean hasSpecialSizes) {
        Sizes size1 = mock(Sizes.class);
        when(size1.getId()).thenReturn("id1");
        Sizes size2 = mock(Sizes.class);
        when(size2.getId()).thenReturn("id2");
        List<Sizes> sizes = Arrays.asList(size1, size2);
        if (hasBackSoon) {
            when(size1.getBackSoon()).thenReturn("true");
            when(size2.getBackSoon()).thenReturn("true");
        }
        if (hasSpecialSizes) {
            when(size1.getSpecial()).thenReturn("true");
            when(size2.getSpecial()).thenReturn("true");
        }
        return sizes;
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