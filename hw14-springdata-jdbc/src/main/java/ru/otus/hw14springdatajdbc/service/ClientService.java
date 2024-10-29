package ru.otus.hw14springdatajdbc.service;

import java.util.List;
import ru.otus.hw14springdatajdbc.model.Client;

public interface ClientService {
    List<Client> findAll();

    void addClient(ClientAddDto clientAddDto);

    void editClient(ClientEditDto clientEditDto);

    void deleteClient(long id);

    Client findById(long id);
}
