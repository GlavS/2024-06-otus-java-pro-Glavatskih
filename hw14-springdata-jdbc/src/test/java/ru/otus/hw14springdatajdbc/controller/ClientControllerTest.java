package ru.otus.hw14springdatajdbc.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw14springdatajdbc.model.Address;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.model.Phone;
import ru.otus.hw14springdatajdbc.service.ClientAddDto;
import ru.otus.hw14springdatajdbc.service.ClientEditDto;
import ru.otus.hw14springdatajdbc.service.ClientService;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllClients() throws Exception {
        Client client = new Client(1L, "John", new Address(2L, "Street1"), Set.of(new Phone(3L, "112233")), false);
        when(clientService.findAll()).thenReturn(List.of(client));
        mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("clients"))
                .andExpect(model().attribute("clients", List.of(client)))
                .andExpect(content().string(containsString("<td>John</td>")))
                .andExpect(content().string(containsString("<td>Street1</td>")))
                .andExpect(content().string(containsString("<li>112233</li>")));
    }

    @Test
    void addNewClientForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>Add new client</h2>")));
    }

    @Test
    void addNewClientAction() throws Exception {
        mockMvc.perform(
                        post("/add")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("clientName", "John")
                                .param("clientAddress", "123 Street")
                                .param("clientPhone", "56565")
                        //  .content("clientName=John&clientAddress=Street+1&clientPhone=56565")
                        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/all"));
        verify(clientService, times(1)).addClient(any(ClientAddDto.class));
    }

    @Test
    void editClientForm() throws Exception {
        long clientId = 1L;
        Client client = new Client(1L, "John", new Address(2L, "Street1"), Set.of(new Phone(3L, "112233")), false);
        when(clientService.findById(clientId)).thenReturn(Optional.of(client));
        mockMvc.perform(get("/edit").param("clientId", String.valueOf(clientId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attribute("client", client));
        verify(clientService, times(1)).findById(clientId);
    }

    @Test
    void editClientAction() throws Exception {
        mockMvc.perform(post("/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("clientID", "1")
                        .param("clientNewName", "John")
                        .param("clientNewAddress", "123 Street")
                        .param("clientNewPhone", "56565"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/all"));
        verify(clientService, times(1)).editClient(any(ClientEditDto.class));
    }

    @Test
    void deleteClient() throws Exception {
        long id = 1L;
        mockMvc.perform(get("/delete")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/all"));
        verify(clientService, times(1)).deleteClient(id);
    }
}
