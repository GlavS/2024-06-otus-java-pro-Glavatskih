package ru.otus.hw14springdatajdbc.model;

import org.springframework.data.annotation.Id;

public record Address(@Id Long id, String street) {}
