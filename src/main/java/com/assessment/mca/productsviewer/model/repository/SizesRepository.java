package com.assessment.mca.productsviewer.model.repository;

import com.assessment.mca.productsviewer.model.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, String> {
}
