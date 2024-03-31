package com.maids.salesManagementSystem.repository;

import com.maids.salesManagementSystem.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByEntityName(String client);
}
