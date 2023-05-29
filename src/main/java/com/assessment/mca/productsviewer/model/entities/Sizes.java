package com.assessment.mca.productsviewer.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "sizes")
public class Sizes {
    @Id
    @Column(length=255, name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTID")
    private Product product;

    @NotBlank
    @Column(length=5, name = "BACKSOON")
    private String backSoon;

    @NotBlank
    @Column(length=5, name = "SPECIAL")
    private String special;

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getBackSoon() {
        return backSoon;
    }

    public String getSpecial() {
        return special;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setBackSoon(String backSoon) {
        this.backSoon = backSoon;
    }

    public void setSpecial(String special) {
        this.special = special;
    }
}
