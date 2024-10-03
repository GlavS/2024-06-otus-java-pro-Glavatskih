package ru.otus.clientservice.service;

import java.util.Optional;
import ru.otus.clientservice.model.Client;

public interface DbServiceClientCached {
    Client saveClient(Client client);

    Optional<Client> getClient(long id);
}
