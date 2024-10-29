package ru.otus.hw14springdatajdbc.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14springdatajdbc.exception.ClientProcessingException;
import ru.otus.hw14springdatajdbc.model.Address;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.model.Phone;
import ru.otus.hw14springdatajdbc.repository.ClientRepository;

@Component
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        Iterable<Client> iterable = clientRepository.findAll();
        List<Client> result = new ArrayList<>();
        iterable.forEach(result::add);
        result.sort(Comparator.comparing(Client::getId));
        return result;
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addClient(ClientAddDto clientAddDto) {
        Long newId = clientRepository.getMaxId() + 1;
        Client newClient = new Client(
                newId,
                clientAddDto.clientName(),
                new Address(null, clientAddDto.clientAddress()),
                Set.of(new Phone(null, clientAddDto.clientPhone())),
                true);
        clientRepository.save(newClient);
    }

    @Override
    @Transactional
    public void editClient(ClientEditDto clientEditDto) {
        Client clientToEdit =
                clientRepository.findById(clientEditDto.clientID()).orElse(null);
        if (clientToEdit == null) {
            throw new ClientProcessingException("Client with id " + clientEditDto.clientID() + " not found");
        }
        Set<Phone> phones = clientToEdit.getPhones();
        if (!clientEditDto.clientNewPhone().isEmpty()) {
            phones.add(new Phone(null, clientEditDto.clientNewPhone()));
        }
        Client clientForSave = new Client(
                clientEditDto.clientID(),
                clientEditDto.clientNewName(),
                new Address(null, clientEditDto.clientNewAddress()),
                phones,
                false);
        clientRepository.save(clientForSave);
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }
}
