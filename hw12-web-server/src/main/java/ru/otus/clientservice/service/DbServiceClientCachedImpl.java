package ru.otus.clientservice.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.SimpleCache;
import ru.otus.clientservice.model.Client;
import ru.otus.hibernate.repository.DataTemplate;
import ru.otus.hibernate.sessionmanager.TransactionManager;

public class DbServiceClientCachedImpl implements DbServiceClientCached {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientCachedImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final SimpleCache<String, Client> cache;

    public DbServiceClientCachedImpl(
            TransactionManager transactionManager,
            DataTemplate<Client> clientDataTemplate,
            SimpleCache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                cache.put(String.valueOf(savedClient.getId()), savedClient);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", savedClient);
            cache.put(String.valueOf(savedClient.getId()), savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            Client cachedClient = cache.get(String.valueOf(id));
            if (cachedClient != null) {
                return Optional.of(cachedClient);
            } else {
                Optional<Client> result = clientDataTemplate.findById(session, id);
                if (result.isPresent()) {
                    cache.put(String.valueOf(id), result.get());
                }
                log.info("client: {}", result);
                return result;
            }
        });
    }
}
