package ru.otus.hw14springdatajdbc.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw14springdatajdbc.model.Address;
import ru.otus.hw14springdatajdbc.model.Client;
import ru.otus.hw14springdatajdbc.model.Phone;
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
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<td>John</td>")))
                .andExpect(content().string(containsString("<td>Street1</td>")))
                .andExpect(content().string(containsString("<li>112233</li>")));
    }
}
