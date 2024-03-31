package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesReport {
    private int totalNumberOfSales;
    private double totalRevenue;
    private List<SalesStatistic> topSellingProducts;
    private List<PerformanceStatistic> topPerformingSellers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
