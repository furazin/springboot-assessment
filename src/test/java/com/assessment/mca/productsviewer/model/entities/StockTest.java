package com.assessment.mca.productsviewer.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class StockTest {

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
    }

    @Test
    void testStock() {
        // given
        Sizes size = mock(Sizes.class);
        stock.setId(Long.valueOf("1"));
        stock.setSize(size);
        stock.setQuantity("10");

        // then
        assertThat(stock.getId(), is(Long.valueOf("1")));
        assertThat(stock.getSize(), is(size));
        assertThat(stock.getQuantity(), is("10"));
    }
}