package com.assessment.mca.productsviewer.model.repository;

import com.assessment.mca.productsviewer.model.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, String> {
    @Query("select s from Sizes s join s.product p where p.id = :productId")
    List<Sizes> findSizesByProductId(@Param("productId") String productId);
}
