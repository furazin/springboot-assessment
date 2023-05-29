package com.assessment.mca.productsviewer.model.repository;

import com.assessment.mca.productsviewer.model.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
