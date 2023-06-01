package com.assessment.mca.productsviewer.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SizesTest {

    private Sizes size;

    @BeforeEach
    void setUp() {
        size = new Sizes();
    }

    @Test
    void testSizes() {
        // given
        Product product = mock(Product.class);
        size.setId("1");
        size.setProduct(product);
        size.setSpecial("false");
        size.setBackSoon("true");

        // then
        assertThat(size.getId(), is("1"));
        assertThat(size.getProduct(), is(product));
        assertThat(size.getBackSoon(), is("true"));
        assertThat(size.getSpecial(), is("false"));
    }
}