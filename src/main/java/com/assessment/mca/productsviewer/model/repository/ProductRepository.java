package com.assessment.mca.productsviewer.model.repository;

import com.assessment.mca.productsviewer.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
