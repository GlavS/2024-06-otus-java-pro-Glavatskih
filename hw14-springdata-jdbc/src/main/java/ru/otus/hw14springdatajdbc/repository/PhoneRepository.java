package ru.otus.hw14springdatajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14springdatajdbc.model.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {}
