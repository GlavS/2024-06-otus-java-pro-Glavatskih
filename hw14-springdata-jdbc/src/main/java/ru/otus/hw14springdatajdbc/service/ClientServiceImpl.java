package ru.otus.hw14springdatajdbc.service;

import java.util.*;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14springdatajdbc.exception.ClientProcessingException;
import ru.otus.hw14springdatajdbc.model.Address;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.model.Phone;
import ru.otus.hw14springdatajdbc.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional
    public void addClient(ClientAddDto clientAddDto) {
        Long newId = clientRepository.getId();
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
