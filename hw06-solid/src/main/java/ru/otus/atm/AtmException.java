package ru.otus.atm;

public class AtmException extends RuntimeException {
    public AtmException(String message, RuntimeException cause) {
        super(message, cause);
    }
}
