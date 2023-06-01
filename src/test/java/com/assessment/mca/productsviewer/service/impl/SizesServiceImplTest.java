package com.assessment.mca.productsviewer.service.impl;

import com.assessment.mca.productsviewer.model.entities.Sizes;
import com.assessment.mca.productsviewer.model.repository.SizesRepository;
import com.assessment.mca.productsviewer.service.SizesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SizesServiceImplTest {

    private SizesService sizesService;
    private final SizesRepository sizesRepository = mock(SizesRepository.class);

    @BeforeEach
    void setUp() {
        sizesService = new SizesServiceImpl(sizesRepository);
    }

    @Test
    void shouldInsertSizesInBBDD() {
        // given
        Sizes size1 = mock(Sizes.class);
        Sizes size2 = mock(Sizes.class);
        List<Sizes> sizes = Arrays.asList(size1, size2);

        // when
        sizesService.insertSizesInBBDD(sizes);

        // then
        verify(sizesRepository).saveAll(anyList());
    }

    @Test
    void givenProductIdShouldFindSizes() {
        // given
        String productId = "productId";
        Sizes size1 = mock(Sizes.class);
        Sizes size2 = mock(Sizes.class);
        List<Sizes> sizes = Arrays.asList(size1, size2);
        when(sizesRepository.findSizesByProductId(anyString())).thenReturn(sizes);

        // when
        List<Sizes> result = sizesService.getSizesFromProductId(productId);

        // then
        assertNotNull(result);
        verify(sizesRepository).findSizesByProductId(anyString());
        assertThat(result, is(sizes));
    }
}