package com.maids.salesManagementSystem.repository;

import com.maids.salesManagementSystem.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sale, Long> {
}
