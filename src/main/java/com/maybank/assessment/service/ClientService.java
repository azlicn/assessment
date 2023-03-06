package com.maybank.assessment.service;

import com.maybank.assessment.entity.Client;
import com.maybank.assessment.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService implements IClientService{

    ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Page<Client> searchClientByNameWithPagination(String firstName, String lastName, Pageable pageable) {

        if (firstName != null && lastName != null) {
            System.out.println("both are not null");
            return clientRepository.findByFirstNameOrLastNameContaining(firstName, lastName, pageable);
        } else if (firstName != null && lastName == null) {
            System.out.println("firstName is not null");
            return clientRepository.findByFirstNameContaining(firstName, pageable);
        } else if (firstName == null && lastName != null) {
            System.out.println("lastName is not null");
            return clientRepository.findByLastNameContaining(lastName, pageable);
        }

        return this.getAllClients(pageable);
    }

    @Override
    @Transactional
    public Client getClientById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
