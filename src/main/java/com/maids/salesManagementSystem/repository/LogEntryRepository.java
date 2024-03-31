package com.maids.salesManagementSystem.repository;

import com.maids.salesManagementSystem.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
