package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Product;
import com.assessment.mca.productsviewer.model.repository.ProductRepository;
import com.assessment.mca.productsviewer.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void shouldInsertProductsInBBDD() {
        // given
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        List<Product> products = Arrays.asList(product1, product2);

        // when
        productService.insertProductsInBBDD(products);

        // then
        verify(productRepository).saveAll(anyList());
    }
}