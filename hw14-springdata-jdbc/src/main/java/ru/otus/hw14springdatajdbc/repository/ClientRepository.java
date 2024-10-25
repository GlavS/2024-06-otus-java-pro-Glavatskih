package ru.otus.hw14springdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14springdatajdbc.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {}
