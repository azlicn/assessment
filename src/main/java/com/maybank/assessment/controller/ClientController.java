package com.maybank.assessment.controller;

import com.maybank.assessment.entity.Client;
import com.maybank.assessment.service.ClientService;
import com.maybank.assessment.thirdparty.CentralBankApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;
    private final CentralBankApiClient centralBankApiClient;

    @Autowired
    public ClientController(ClientService clientService, CentralBankApiClient centralBankApiClient) {
        this.clientService = clientService;
        this.centralBankApiClient = centralBankApiClient;
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<Map<String, Object>> getAllClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {

        try {
            List<Client> clients;
            Pageable paging = PageRequest.of(page, size);

            Page<Client> clientPage;
            if (firstName == null)
                clientPage = clientService.getAllClients(paging);
            else
                clientPage = clientService.searchClientByNameWithPagination(firstName, paging);

            clients = clientPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("clients", clients);
            response.put("currentPage", clientPage.getNumber());
            response.put("totalItems", clientPage.getTotalElements());
            response.put("totalPages", clientPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> get(@PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> add(@RequestBody Client client) {
        try {
            Client savedClient = clientService.save(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> update(@RequestBody Client client, @PathVariable Long id) {
        try {
            Client existProduct = clientService.getClientById(id);
            existProduct.setFirstName(client.getFirstName());
            existProduct.setLastName(client.getLastName());
            existProduct.setEmail(client.getEmail());
            Client updatedCLient = clientService.save(existProduct);
            return new ResponseEntity<>(updatedCLient, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/centralBank")
    public ResponseEntity<String> nestedApiCall() {
        /*String response = centralBankApiClient.callThirdPartyApi();
        return ResponseEntity.ok(response);*/
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://api.instantwebtools.net/v1/airlines/1", String.class);

        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
