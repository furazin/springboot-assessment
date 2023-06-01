package com.assessment.mca.productsviewer.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void testProduct() {
        // given
        product.setId("1");
        product.setSeq("10");
        assertThat(product.getId(), is("1"));
        assertThat(product.getSeq(), is("10"));
    }
}