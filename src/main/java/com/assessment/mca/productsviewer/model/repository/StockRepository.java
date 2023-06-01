package com.assessment.mca.productsviewer.model.repository;

import com.assessment.mca.productsviewer.model.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    @Query("select st from Stock st join st.size si where si.id = :sizeId")
    List<Stock> findStocksBySizesId(@Param("sizeId") String sizeId);
}
