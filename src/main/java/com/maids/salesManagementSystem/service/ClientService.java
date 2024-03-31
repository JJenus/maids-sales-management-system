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
    @Autowired
    private LogService logService;

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

    ;

    public Client updateClient(Long id, Client updatedClient) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));

        // Check for changes in the client data and log updates
        if (!existingClient.getName().equals(updatedClient.getName())) {
            logService.logUpdate(existingClient, "Name", existingClient.getName(), updatedClient.getName());
            existingClient.setName(updatedClient.getName());
        }
        if (!existingClient.getLastName().equals(updatedClient.getLastName())) {
            logService.logUpdate(existingClient, "Last Name", existingClient.getLastName(), updatedClient.getLastName());
            existingClient.setLastName(updatedClient.getLastName());
        }
        if (!existingClient.getMobile().equals(updatedClient.getMobile())) {
            logService.logUpdate(existingClient, "Mobile", existingClient.getMobile(), updatedClient.getMobile());
            existingClient.setMobile(updatedClient.getMobile());
        }
        if (!existingClient.getEmail().equals(updatedClient.getEmail())) {
            logService.logUpdate(existingClient, "Email", existingClient.getEmail(), updatedClient.getEmail());
            existingClient.setEmail(updatedClient.getEmail());
        }
        if (!existingClient.getAddress().equals(updatedClient.getAddress())) {
            logService.logUpdate(existingClient, "Address", existingClient.getAddress(), updatedClient.getAddress());
            existingClient.setAddress(updatedClient.getAddress());
        }

        // Save the updated client
        return clientRepository.save(existingClient);
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
        clientReport.setClientActivities(logService.findActivitiesByEntity("Client"));

        return  clientReport;
    }
}

// ProductService and SalesService can be similarly updated to handle ProductNotFoundException and SaleNotFoundException respectively.


