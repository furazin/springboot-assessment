package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.service.SizesService;
import com.assessment.mca.productsviewer.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VisibleProductsServiceImplTest {

    private final SizesService sizesService = mock(SizesService.class);

    private final StockService stockService = mock(StockService.class);

    private VisibleProductsServiceImpl visibleProductsService = new VisibleProductsServiceImpl(sizesService, stockService);

    private List<Product> products;
    private List<Sizes> sizes;
    private List<Stock> stocks;

    @BeforeEach
    void setUp() {
        products = buildProducts();
        sizes = buildSizes(false, false);
        stocks = buildStocks(true);

        when(sizesService.getSizesFromProductId(anyString())).thenReturn(sizes);
        when(stockService.getStocksFromSizeId(anyString())).thenReturn(stocks);
    }

    @Test
    void shouldView(){
        // when
        List<String> result = visibleProductsService.getVisibleProducts(products);

        // then
        assertNotNull(result);
        verify(sizesService, times(2)).getSizesFromProductId(anyString());
        verify(stockService, times(2)).getStocksFromSizeId(anyString());
    }

//    @Test
//    void shouldNotViewAnyProduct() throws IOException {
//        // given
//        when(sizesService.getSizesFromProductId(anyString())).thenReturn(new ArrayList<>());
//        when(stockService.getStocksFromSizeId(anyString())).thenReturn(new ArrayList<>());
//
//        // when
//        List<String> result = visibleProductsService.getVisibleProducts(products);
//
//        // then
//        assertNotNull(result);
//        verify(sizesService, times(0)).getSizesFromProductId(anyString());
//        verify(stockService, times(0)).getStocksFromSizeId(anyString());
//    }

    @Test
    void shouldNotViewAnyProductWhenNoStock() throws IOException {
        // given
        stocks = buildStocks(false);
        when(stockService.getStocksFromSizeId(anyString())).thenReturn(stocks);

        // when
        List<String> result = visibleProductsService.getVisibleProducts(products);

        // then
        assertNotNull(result);
        verify(sizesService, times(2)).getSizesFromProductId(anyString());
        verify(stockService, times(4)).getStocksFromSizeId(anyString());
    }

    @Test
    void shouldViewProductsWithBackSoonSizes() throws IOException {
        // given
        List<Sizes> sizes = buildSizes(true, false);
        when(sizesService.getSizesFromProductId(anyString())).thenReturn(sizes);

        // when
        List<String> result = visibleProductsService.getVisibleProducts(products);

        // then
        assertNotNull(result);
        verify(sizesService, times(2)).getSizesFromProductId(anyString());
        verify(stockService, times(2)).getStocksFromSizeId(anyString());
    }

    @Test
    void shouldViewProductsWithBackSpecialSizes() throws IOException {
        // given
        List<Sizes> sizes = buildSizes(false, true);
        when(sizesService.getSizesFromProductId(anyString())).thenReturn(sizes);

        // when
        List<String> result = visibleProductsService.getVisibleProducts(products);

        // then
        assertNotNull(result);
        verify(sizesService, times(2)).getSizesFromProductId(anyString());
        verify(stockService, times(2)).getStocksFromSizeId(anyString());
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