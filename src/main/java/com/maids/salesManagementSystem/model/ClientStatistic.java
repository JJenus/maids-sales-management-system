package com.maids.salesManagementSystem.model;

import com.maids.salesManagementSystem.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientStatistic implements Comparable<ClientStatistic> {
    private Client client;
    private double totalSpent;

    // Constructor, getters, and setters

    @Override
    public int compareTo(ClientStatistic other) {
        // Compare based on totalSpent
        return Double.compare(other.totalSpent, this.totalSpent);
    }
}
