package ru.otus.hw14springdatajdbc.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14springdatajdbc.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("SELECT nextval('client_id_seq')")
    Long getId();
    List<Client> findAll(Sort sort);
}
