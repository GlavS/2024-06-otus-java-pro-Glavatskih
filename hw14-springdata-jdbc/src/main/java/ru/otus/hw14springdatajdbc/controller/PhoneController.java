package ru.otus.hw14springdatajdbc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw14springdatajdbc.repository.PhoneRepository;

@Controller
public class PhoneController {

    private final PhoneRepository phoneRepository;
    public PhoneController(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @GetMapping("/delete/phone")
    public String deletePhone(Long phoneId, Long clientId) {
        phoneRepository.deleteById(phoneId);
        return "redirect:/edit?clientId=" + clientId;
    }
}
