package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesStatistic {
    private Product product;
    private int quantitySold;
}
