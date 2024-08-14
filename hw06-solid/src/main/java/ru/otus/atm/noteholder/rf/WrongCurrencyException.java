package ru.otus.atm.noteholder.rf;

public class WrongCurrencyException extends RuntimeException {
    public WrongCurrencyException(String message) {
        super(message);
    }
}
