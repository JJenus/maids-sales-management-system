package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.LogEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientReport {
    private int totalNumberOfClients;
    private List<ClientStatistic> topSpendingClients;
    private List<LogEntry> clientActivities;
}
