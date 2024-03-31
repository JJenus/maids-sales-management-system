package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Client;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceStatistic {
    private Client seller;
    private double totalRevenue;
}
