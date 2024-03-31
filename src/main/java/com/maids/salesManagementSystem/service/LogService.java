package com.maids.salesManagementSystem.service;

import com.maids.salesManagementSystem.entity.Auditable;
import com.maids.salesManagementSystem.entity.LogEntry;
import com.maids.salesManagementSystem.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogEntryRepository logEntryRepository;

    public void saveLog(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }

    public void logUpdate(Auditable entity, String field, String oldValue, String newValue) {
        // Get the entity class name
        String entityName = entity.getClass().getSimpleName();

        // Get the entity ID using the Auditable interface
        Long entityId = entity.getId();

        // Create a LogEntry for the entity update
        LogEntry logEntry = new LogEntry();
        logEntry.setActivity(entityName + " Update");
        logEntry.setEntityName(entityName);
        logEntry.setEntityId(entityId);
        logEntry.setFieldChanged(field);
        logEntry.setOldValue(oldValue);
        logEntry.setNewValue(newValue);

        // Save the LogEntry to the database
        logEntryRepository.save(logEntry);
    }
}

