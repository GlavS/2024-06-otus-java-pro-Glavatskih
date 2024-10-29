package ru.otus.hw14springdatajdbc.exception;

public class ClientProcessingException extends RuntimeException {
    public ClientProcessingException(String message) {
        super(message);
    }
}
