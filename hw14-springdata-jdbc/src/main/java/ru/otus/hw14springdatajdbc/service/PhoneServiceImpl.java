package ru.otus.hw14springdatajdbc.service;

import org.springframework.stereotype.Service;
import ru.otus.hw14springdatajdbc.repository.PhoneRepository;

@Service
public class PhoneServiceImpl implements ru.otus.hw14springdatajdbc.service.PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public void delete(Long id) {
        phoneRepository.deleteById(id);
    }
}
