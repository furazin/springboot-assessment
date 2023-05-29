package com.assessment.mca.productsviewer.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="product")
public class Product {
    @Id
    @Column(length=255, name = "ID")
    private String id;

    @NotBlank
    @Column(length=255, name = "SEQ")
    private String seq;

    public String getId() {
        return id;
    }

    public String getSeq() {
        return seq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
