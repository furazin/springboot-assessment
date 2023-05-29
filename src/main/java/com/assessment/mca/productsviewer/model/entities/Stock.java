package com.assessment.mca.productsviewer.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @Column(length=255, name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIZEID")
    private Sizes size;

    @NotBlank
    @Column(length=5)
    private String quantity;

    public String getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public Sizes getSize() {
        return size;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSize(Sizes size) {
        this.size = size;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
