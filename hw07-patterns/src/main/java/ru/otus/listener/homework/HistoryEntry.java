package ru.otus.listener.homework;

import java.time.LocalDateTime;
import ru.otus.model.Message;

public record HistoryEntry(Message message, LocalDateTime time) {}
