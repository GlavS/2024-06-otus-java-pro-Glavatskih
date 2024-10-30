package ru.otus.hw14springdatajdbc.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw14springdatajdbc.model.Address;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.model.Phone;
import ru.otus.hw14springdatajdbc.repository.ClientRepository;

@SpringBootTest(classes = ClientServiceImpl.class)
class ClientServiceImplTest {

    @Autowired
    private ClientServiceImpl clientService;

    @MockBean
    private ClientRepository clientRepository;

    private final Client client1 =
            new Client(1L, "Client1", new Address(1L, "Street 1"), Set.of(new Phone(1L, "1111")), false);
    private final Client client2 =
            new Client(2L, "Client2", new Address(2L, "Street 2"), Set.of(new Phone(2L, "2222")), false);

    @Test
    void shouldGetAllClientsCorrectly() {
        when(clientRepository.findAll()).thenReturn(List.of(client1, client2));
        assertThat(clientService.findAll()).containsExactly(client1, client2);
    }

    @Test
    void shouldGetClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));
        assertThat(clientService.findById(1)).usingRecursiveComparison().isEqualTo(client1);
    }
}
