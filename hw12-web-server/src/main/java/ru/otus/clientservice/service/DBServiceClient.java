package ru.otus.clientservice.service;

import java.util.List;
import ru.otus.clientservice.model.Client;

public interface DBServiceClient extends DbServiceClientCached {
    List<Client> findAll();
}
