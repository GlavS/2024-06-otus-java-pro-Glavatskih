package ru.otus.hw14springdatajdbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw14springdatajdbc.exception.ClientProcessingException;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.service.ClientAddDto;
import ru.otus.hw14springdatajdbc.service.ClientEditDto;
import ru.otus.hw14springdatajdbc.service.ClientService;

@Controller
public class ClientController {

    private static final String REDIRECT_ALL = "redirect:/all";
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public String allClients(Model model) {
        var clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "index";
    }

    @GetMapping("/add")
    public String addClientForm() {
        return "add";
    }

    @PostMapping("/add")
    public String addClientAction(ClientAddDto clientAddDto) {
        clientService.addClient(clientAddDto);
        return REDIRECT_ALL;
    }

    @GetMapping("/edit")
    public String editClientForm(Long clientId, Model model) {
        Client clientToEdit = clientService.findById(clientId);
        if (clientToEdit == null) {
            throw new ClientProcessingException("Client with id " + clientId + " not found");
        }
        model.addAttribute("client", clientToEdit);
        return "edit";
    }

    @PostMapping("/edit")
    public String editClientAction(ClientEditDto clientEditDto) {
        clientService.editClient(clientEditDto);
        return REDIRECT_ALL;
    }

    @GetMapping("/delete")
    public String deleteClient(Long id) {
        clientService.deleteClient(id);
        return REDIRECT_ALL;
    }
}
