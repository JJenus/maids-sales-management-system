package com.maids.salesManagementSystem.repository;

import com.maids.salesManagementSystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}
