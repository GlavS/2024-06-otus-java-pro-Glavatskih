package ru.otus.timeprovider;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime getTime();
}
