package com.maids.salesManagementSystem.repository;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.Product;
import com.maids.salesManagementSystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByProduct(Product product);
}
