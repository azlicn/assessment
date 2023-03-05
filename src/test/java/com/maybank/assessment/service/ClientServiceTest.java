package com.maybank.assessment.service;

import com.maybank.assessment.entity.Client;
import com.maybank.assessment.repo.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetClientById() {
        Long id = 1L;
        Client expectedClient = new Client();
        expectedClient.setId(id);
        expectedClient.setFirstName("Ahmad");
        expectedClient.setLastName("Ali");
        expectedClient.setEmail("ahmad.ali@gmail.com");

        when(clientRepository.findById(id)).thenReturn(Optional.of(expectedClient));

        Client actualClient = clientService.getClientById(id);

        assertThat(actualClient).isNotNull();
        assertThat(actualClient.getId()).isEqualTo(expectedClient.getId());
        assertThat(actualClient.getFirstName()).isEqualTo(expectedClient.getFirstName());
    }
}
