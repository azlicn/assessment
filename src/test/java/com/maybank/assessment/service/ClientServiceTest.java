package com.maybank.assessment.service;

import com.maybank.assessment.entity.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientService clientService;

    @Test
    public void testGetClientById() {
        Long id = 1L;
        Client expectedClient = new Client();
        expectedClient.setId(id);
        expectedClient.setFirstName("Ahmad");
        expectedClient.setLastName("Ali");
        expectedClient.setEmail("ahmad.ali@gmail.com");
        when(clientService.getClientById(1L)).thenReturn(expectedClient);
        assertEquals(clientService.getClientById(1L).getFirstName(), "Ahmad");
        assertEquals(clientService.getClientById(1L).getLastName(), "Ali");
        assertEquals(clientService.getClientById(1L).getEmail(), "ahmad.ali@gmail.com");
    }

    @Test
    public void testSaveClient() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Mohd");
        client.setLastName("Rasul");
        client.setEmail("mohd@gmail.com");
        clientService.saveClient(client);
        verify(clientService, times(1)).saveClient(client);
        ArgumentCaptor<Client> orderArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientService).saveClient(orderArgumentCaptor.capture());
        Client clientCreated = orderArgumentCaptor.getValue();
        assertNotNull(clientCreated.getId());
        assertEquals("Mohd", clientCreated.getFirstName());
    }

    @Test
    public void testDeleteClient() {

        Client client = new Client();
        client.setId(13L);
        client.setFirstName("Abdul");
        client.setLastName("Rahim");
        client.setEmail("abdul@gmail.com");
        clientService.deleteClient(client.getId());
        verify(clientService, times(1)).deleteClient(client.getId());
        ArgumentCaptor<Long> orderArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(clientService).deleteClient(orderArgumentCaptor.capture());
        Long orderIdDeleted = orderArgumentCaptor.getValue();
        assertNotNull(orderIdDeleted);
        assertEquals(13L, orderIdDeleted);
    }

}
