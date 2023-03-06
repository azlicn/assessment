package com.maybank.assessment.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.assessment.entity.Client;
import com.maybank.assessment.service.ClientService;
import com.maybank.assessment.service.LoggingService;
import com.maybank.assessment.thirdparty.ThirdPartyClientApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClientService clientService;
    @MockBean
    private ThirdPartyClientApi thirdPartyClientApi;
    @MockBean
    private LoggingService loggingService;


    @Test
    void shouldReturnAllClientsWithPagination() throws Exception {

        Pageable pageRequest = PageRequest.of(1, 10);
        List<Client> clients = Arrays.asList(new Client(), new Client());
        Page<Client> clientsWitPagination = new PageImpl<>(clients, pageRequest, clients.size());

        given(clientService.getAllClients(pageRequest)).willReturn(clientsWitPagination);

        mockMvc.perform(get("/api/clients")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnClient() throws Exception {
        Long id = 1L;
        Client client = new Client();
        client.setId(id);
        client.setFirstName("Mohd");
        client.setLastName("Rasul");
        client.setEmail("mohd@gmail.com");

        when(clientService.getClientById(1L)).thenReturn((client));

        mockMvc.perform(get("/api/clients/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(client.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.email").value(client.getEmail()))
                .andDo(print());

    }

    @Test
    void shouldCreateClient() throws Exception {

        Client client = new Client();
        client.setFirstName("Abu");
        client.setLastName("Ali");
        client.setEmail("ali@gmail.com");

        mockMvc.perform(post("/api/clients").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldUpdateTutorial() throws Exception {
        long id = 1L;

        Client client = new Client();
        client.setFirstName("Fname");
        client.setLastName("Lname");
        client.setEmail("email@gmail.com");

        Client updatedClient = new Client();
        updatedClient.setFirstName("Update Fname");
        updatedClient.setLastName("Update Lname");
        updatedClient.setEmail("updated.email@gmail.com");

        when(clientService.getClientById(id)).thenReturn(client);
        when(clientService.saveClient(ArgumentMatchers.any(Client.class))).thenReturn(updatedClient);

        mockMvc.perform(put("/api/clients/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedClient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updatedClient.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedClient.getLastName()))
                .andExpect(jsonPath("$.email").value(updatedClient.getEmail()))
                .andDo(print());
    }

    @Test
    void shouldDeleteClient() throws Exception {
        long id = 1L;

        doNothing().when(clientService).deleteClient(id);
        mockMvc.perform(delete("/api/clients/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
