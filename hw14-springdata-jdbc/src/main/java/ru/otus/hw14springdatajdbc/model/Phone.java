package ru.otus.hw14springdatajdbc.model;

import org.springframework.data.annotation.Id;

public record Phone(@Id Long id, String number) {}
