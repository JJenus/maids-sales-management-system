package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReport {
    private Product product;
    private String inventoryStatus;
    private int totalUnitsSold;
    private double totalRevenue;
    private double averagePrice;
    private double minPrice;
    private double maxPrice;
    private double priceVariance;
}
