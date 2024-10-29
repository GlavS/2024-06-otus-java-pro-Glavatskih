package ru.otus.hw14springdatajdbc.service;

public record ClientEditDto(Long clientID, String clientNewName, String clientNewAddress, String clientNewPhone) {}
