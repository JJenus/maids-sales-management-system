package com.maids.salesManagementSystem.service;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.exception.ClientAlreadyExistsException;
import com.maids.salesManagementSystem.exception.ClientNotFoundException;
import com.maids.salesManagementSystem.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// ClientService
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

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
}

// ProductService and SalesService can be similarly updated to handle ProductNotFoundException and SaleNotFoundException respectively.


