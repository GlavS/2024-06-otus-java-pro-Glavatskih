package ru.otus.hw14springdatajdbc.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw14springdatajdbc.service.PhoneService;

@WebMvcTest(PhoneController.class)
class PhoneControllerTest {
    @MockBean
    private PhoneService phoneService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deletePhone() throws Exception {
        long phoneId = 1L;
        long clientId = 1L;
        mockMvc.perform(get("/delete/phone")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("phoneId", String.valueOf(phoneId))
                        .param("clientId", String.valueOf(clientId)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit?clientId=" + clientId));
        verify(phoneService, times(1)).delete(phoneId);
    }
}
