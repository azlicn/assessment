package com.maybank.assessment.service;

import com.maybank.assessment.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientService {

    Page<Client> getAllClients(Pageable pageable);
    Page<Client> searchClientByNameWithPagination(String firstName, String lastName, Pageable pageable);
    Client getClientById(Long id);
    Client saveClient(Client client);
    void deleteClient(Long id);


}
