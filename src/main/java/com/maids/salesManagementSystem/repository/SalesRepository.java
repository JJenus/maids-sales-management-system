package com.maids.salesManagementSystem.repository;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Sale> findByClient(Client client);
}
