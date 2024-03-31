package com.maids.salesManagementSystem.conroller;

import com.maids.salesManagementSystem.model.ClientReport;
import com.maids.salesManagementSystem.model.ProductReport;
import com.maids.salesManagementSystem.model.SalesReport;
import com.maids.salesManagementSystem.service.ClientService;
import com.maids.salesManagementSystem.service.ProductService;
import com.maids.salesManagementSystem.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    // Endpoint to generate a sales report for a specific date range
    @GetMapping("/sales")
    public ResponseEntity<SalesReport> generateSalesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        SalesReport salesReport = salesService.generateSalesReport(startDate, endDate);
        return ResponseEntity.ok(salesReport);
    }

    // Endpoint to generate a client report
    @GetMapping("/clients")
    public ResponseEntity<ClientReport> generateClientReport() {
        ClientReport clientReport = clientService.generateClientReport();
        return ResponseEntity.ok(clientReport);
    }

    // Endpoint to generate a product report
    @GetMapping("/products")
    public ResponseEntity<List<ProductReport>> generateProductReport() {
        List<ProductReport> productReport = productService.generateProductReport();
        return ResponseEntity.ok(productReport);
    }
}

