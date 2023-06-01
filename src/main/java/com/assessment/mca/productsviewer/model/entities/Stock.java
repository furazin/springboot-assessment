package com.assessment.mca.productsviewer.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_SEQ")
    @SequenceGenerator(sequenceName = "stock_seq", name = "STOCK_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIZEID")
    private Sizes size;

    @NotBlank
    @Column(length=5)
    private String quantity;

    public Long getId() {
        return id;
    }

    public String getQuantity() {
        return quantity;
    }

    public Sizes getSize() {
        return size;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSize(Sizes size) {
        this.size = size;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
