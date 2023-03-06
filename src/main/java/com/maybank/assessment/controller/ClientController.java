package com.maybank.assessment.controller;

import com.maybank.assessment.entity.Client;
import com.maybank.assessment.service.ClientService;
import com.maybank.assessment.thirdparty.ThirdPartyClientApi;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;
    private final ThirdPartyClientApi thirdPartyClientApi;

    @Autowired
    public ClientController(ClientService clientService, ThirdPartyClientApi thirdPartyClientApi) {
        this.clientService = clientService;
        this.thirdPartyClientApi = thirdPartyClientApi;
    }

    /**
     * Search all client by name,
     * @param firstName
     * @param lastName
     * @param page
     * @param size
     * @return a list of client with pagination
     */
    @GetMapping(value = "/clients")
    @ApiOperation(value = "Get all clients", response=Client.class)
    public ResponseEntity<Map<String, Object>> getAllClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {

        try {
            List<Client> clients;
            Pageable paging = PageRequest.of(page, size);

            Page<Client> clientPage;
            if (firstName == null && lastName == null)
                clientPage = clientService.getAllClients(paging);
            else
                clientPage = clientService.searchClientByNameWithPagination(firstName, lastName, paging);

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

    /**
     * Get client by id
     * @param id
     * @return a single client
     */
    @GetMapping("/clients/{id}")
    @ApiOperation(value = "Get client by Id", notes="Please provide valid client id to get the client info", response=Client.class)
    public ResponseEntity<Client> get(@PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new client
     * @param client
     * @return a saved client
     */
    @PostMapping("/clients")
    @ApiOperation(value = "Create a new client", response=Client.class)
    public ResponseEntity<Client> create(@RequestBody Client client) {
        try {
            Client savedClient = clientService.saveClient(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an existing client
     * @param client
     * @param id
     * @return an updated client
     */
    @PutMapping("/clients/{id}")
    @ApiOperation(value = "Update an existing client", response=Client.class)
    public ResponseEntity<Client> update(@RequestBody Client client, @PathVariable Long id) {
        try {
            Client existProduct = clientService.getClientById(id);
            existProduct.setFirstName(client.getFirstName());
            existProduct.setLastName(client.getLastName());
            existProduct.setEmail(client.getEmail());
            Client updatedCLient = clientService.saveClient(existProduct);
            return new ResponseEntity<>(updatedCLient, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a client
     * @param id
     * @return no value
     */
    @DeleteMapping("/clients/{id}")
    @ApiOperation(value = "Delete an existing client")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Call a third party api client
     * @return string value
     */
    @GetMapping("/opr")
    @ApiOperation(value = "Call nested Api")
    public ResponseEntity<String> callNestedApi() {
        String response = thirdPartyClientApi.callThirdPartyApi();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
