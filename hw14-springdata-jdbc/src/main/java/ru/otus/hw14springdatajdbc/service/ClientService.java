package ru.otus.hw14springdatajdbc.service;

import java.util.List;
import java.util.Optional;
import ru.otus.hw14springdatajdbc.model.Client;

public interface ClientService {
    List<Client> findAll();

    void addClient(ClientAddDto clientAddDto);

    void editClient(ClientEditDto clientEditDto);

    void deleteClient(long id);

    Optional<Client> findById(long id);
}
