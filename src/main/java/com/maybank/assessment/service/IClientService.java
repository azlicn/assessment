package com.maybank.assessment.service;

import com.maybank.assessment.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientService {

    Page<Client> getAllClients(Pageable pageable);
    Page<Client> searchClientByNameWithPagination(String firstName, Pageable pageable);
    Client getClientById(Long id);
    Client findByEmail(String email);
    Client save(Client client);
    void delete(Long id);


}
