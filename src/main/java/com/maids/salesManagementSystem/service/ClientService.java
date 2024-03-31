package com.maids.salesManagementSystem.service;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.Sale;
import com.maids.salesManagementSystem.entity.Transaction;
import com.maids.salesManagementSystem.exception.ClientAlreadyExistsException;
import com.maids.salesManagementSystem.exception.ClientNotFoundException;
import com.maids.salesManagementSystem.model.ClientReport;
import com.maids.salesManagementSystem.model.ClientStatistic;
import com.maids.salesManagementSystem.repository.ClientRepository;
import com.maids.salesManagementSystem.repository.SalesRepository;
import com.maids.salesManagementSystem.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ClientService
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SalesRepository salesRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ClientAlreadyExistsException("Client with email already exists: " + client.getEmail());
        }
        if (clientRepository.existsByMobile(client.getMobile())) {
            throw new ClientAlreadyExistsException("Client with mobile already exists: " + client.getMobile());
        }
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        if (!existingClient.getEmail().equals(client.getEmail()) && clientRepository.existsByEmail(client.getEmail())) {
            throw new ClientAlreadyExistsException("Client with email already exists: " + client.getEmail());
        }
        if (!existingClient.getMobile().equals(client.getMobile()) && clientRepository.existsByMobile(client.getMobile())) {
            throw new ClientAlreadyExistsException("Client with mobile already exists: " + client.getMobile());
        }
        client.setId(id);
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public List<ClientStatistic> calculateClientStatistics(List<Client> clients) {
        List<ClientStatistic> clientStatistics = new ArrayList<>();

        for (Client client : clients) {
            double totalSpent = calculateTotalSpent(client);
            ClientStatistic statistic = new ClientStatistic(client, totalSpent);
            clientStatistics.add(statistic);
        }

        return clientStatistics;
    }

    public List<ClientStatistic> findTopSpendingClients(List<Client> clients) {
        List<ClientStatistic> clientStatistics = calculateClientStatistics(clients);
        Collections.sort(clientStatistics);
        return clientStatistics;
    }

    private double calculateTotalSpent(Client client) {
        // Retrieve transactions for the client and calculate total spent
        List<Sale> sales = salesRepository.findByClient(client);
        double totalSpent = 0.0;
        for (Sale sale : sales) {
            totalSpent += sale.getTotal();
        }
        return totalSpent;
    }

    public ClientReport generateClientReport() {
        ClientReport clientReport = new ClientReport();

        List<Client> clients = clientRepository.findAll();
        clientReport.setTotalNumberOfClients(clients.size());

        clientReport.setTopSpendingClients(findTopSpendingClients(clients));

        return  clientReport;
    }
}

// ProductService and SalesService can be similarly updated to handle ProductNotFoundException and SaleNotFoundException respectively.


