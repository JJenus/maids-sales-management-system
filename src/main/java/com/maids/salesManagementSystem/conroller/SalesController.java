package com.maids.salesManagementSystem.conroller;

import com.maids.salesManagementSystem.entity.Sale;
import com.maids.salesManagementSystem.model.SaleDTO;
import com.maids.salesManagementSystem.service.SalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private SalesService salesService;

    @GetMapping
    public List<Sale> getAllSales() {
        return salesService.getAllSales();
    }

    @PostMapping
    public Sale createSale(@Valid @RequestBody SaleDTO sale) {
        return salesService.createSale(sale);
    }

    @PutMapping("/{id}")
    public Sale updateSale(@PathVariable Long id, @Valid @RequestBody Sale sale) {
        return salesService.updateSale(id, sale);
    }

    // Add more endpoints for sales operations
}
