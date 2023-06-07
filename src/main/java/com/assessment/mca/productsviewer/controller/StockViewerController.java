package com.assessment.mca.productsviewer.controller;

import com.assessment.mca.productsviewer.service.CsvDataService;
import com.assessment.mca.productsviewer.service.VisibleProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/store", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class StockViewerController {
    private final CsvDataService csvDataService;
    private final VisibleProductsService visibleProductsService;

    @Autowired
    public StockViewerController(CsvDataService csvDataService, VisibleProductsService visibleProductsService) {
        this.csvDataService = csvDataService;
        this.visibleProductsService = visibleProductsService;
    }

    @GetMapping("/view")
    public List<String> view(){
        try {
            csvDataService.readAndPersistData();

            return visibleProductsService.getVisibleProducts();
        }catch(Exception exception) {
            log.error("error in reading csv");
            throw new RuntimeException(exception);
        }
    }
}