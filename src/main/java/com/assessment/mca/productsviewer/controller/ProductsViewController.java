package com.assessment.mca.productsviewer.controller;

import com.assessment.mca.productsviewer.model.entities.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsViewController {

    @GetMapping("/view")
    public List<Product> view() {

        // TODO: Implement products views

        return null;
    }
}