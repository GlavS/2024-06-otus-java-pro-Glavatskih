package ru.otus.hw14springdatajdbc.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.hw14springdatajdbc.model.Address;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.model.Phone;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12");



    @Autowired
    private ClientRepository clientRepository;

    @Test
    void findById() {
        var client = clientRepository.findById(1L);
        assertNotNull(client);
    }

    @Test
    void findAll() {
        var client = clientRepository.findAll();
        assertNotNull(client);
    }

    @Test
    void save() {
        var client = new Client(
                5L, "Carl", new Address(null, "Fridrichstrasse 2"), Set.of(new Phone(null, "222222222")), true);
        clientRepository.save(client);
        assertNotNull(clientRepository.findById(5L));
    }

    @Test
    void update() {
        var client = new Client(
                6L, "Carl", new Address(null, "Fridrichstrasse 2"), Set.of(new Phone(null, "222222222")), true);
        clientRepository.save(client);
        client = new Client(
                6L, "Carl", new Address(null, "Leninstrasse 30"), Set.of(new Phone(null, "44444444")), false);
        clientRepository.save(client);
        assertNotNull(clientRepository.findById(5L));
    }
}
