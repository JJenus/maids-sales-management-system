package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Product;
import com.maids.salesManagementSystem.entity.Sale;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
public class TransactionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @NotNull
    private int quantity;

    @DecimalMin(value = "0.0")
    private double price;
}
