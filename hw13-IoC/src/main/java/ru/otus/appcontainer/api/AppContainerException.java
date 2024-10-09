package ru.otus.appcontainer.api;

public class AppContainerException extends RuntimeException {
    public AppContainerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppContainerException(String message) {
        super(message);
    }
}
