package com.maids.salesManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull
    private Client client;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @NotNull
    private Client seller;

    @DecimalMin(value = "0.0")
    private double total;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}