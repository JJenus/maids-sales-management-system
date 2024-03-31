package com.maids.salesManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activity;

    private String entityName;

    private Long entityId;

    private String fieldChanged;

    private String oldValue;

    private String newValue;
    private LocalDateTime createdAt;

    @PrePersist
    public void onInsert(){
        createdAt = LocalDateTime.now();
    }
}

