package ru.otus.hw14springdatajdbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw14springdatajdbc.service.PhoneService;

@Controller
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/delete/phone")
    public String deletePhone(Long phoneId, Long clientId) {
        phoneService.delete(phoneId);
        return "redirect:/edit?clientId=" + clientId;
    }
}
