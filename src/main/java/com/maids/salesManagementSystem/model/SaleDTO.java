package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.Sale;
import com.maids.salesManagementSystem.entity.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SaleDTO {
    private Long id;

    @NotNull
    private Client client;

    @NotNull
    private Client seller;

    @DecimalMin(value = "0.0")
    private double total;
    private LocalDateTime creationDate;

    private List<Transaction> transactions;

}