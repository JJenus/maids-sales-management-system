package com.maids.salesManagementSystem.service;

import com.maids.salesManagementSystem.entity.Sale;
import com.maids.salesManagementSystem.exception.SaleNotFoundException;
import com.maids.salesManagementSystem.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesService {
    @Autowired
    private SalesRepository salesRepository;

    public List<Sale> getAllSales() {
        return salesRepository.findAll();
    }

    public Sale createSale(Sale sale) {
        return salesRepository.save(sale);
    }

    public Sale updateSale(Long id, Sale sale) {
        if (!salesRepository.existsById(id)) {
            throw new SaleNotFoundException("Sale not found with id: " + id);
        }
        sale.setId(id);
        return salesRepository.save(sale);
    }

}
