package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Stock;
import com.assessment.mca.productsviewer.model.repository.StockRepository;
import com.assessment.mca.productsviewer.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class StockServiceImplTest {

    private StockService stockService;
    private final StockRepository stockRepository = mock(StockRepository.class);

    @BeforeEach
    void setUp() {
        stockService = new StockServiceImpl(stockRepository);
    }

    @Test
    void shouldInsertStocks() {
        // given
        Stock stock1 = mock(Stock.class);
        Stock stock2 = mock(Stock.class);
        List<Stock> stocks = Arrays.asList(stock1, stock2);

        // when
        stockService.insertStocksInBBDD(stocks);

        // then
        verify(stockRepository).saveAll(anyList());
    }

    @Test
    void givenSizeIdShouldFindStocks() {
        // given
        String sizeId = "SIZEID";
        Stock stock1 = mock(Stock.class);
        Stock stock2 = mock(Stock.class);
        List<Stock> stocks = Arrays.asList(stock1, stock2);
        when(stockRepository.findStocksBySizesId(sizeId)).thenReturn(stocks);

        // when
        List<Stock> result = stockService.getStocksFromSizeId(sizeId);

        // then
        assertNotNull(result);
        verify(stockRepository).findStocksBySizesId(anyString());
        assertThat(result, is(stocks));
    }
}