package ru.otus.processor;

import java.time.LocalDateTime;
import ru.otus.model.Message;

public class ProcessorExceptionEvenSecondStart implements Processor {
    @Override
    public Message process(Message message) {
        if (LocalDateTime.now().getSecond() % 2 == 0) {
            throw new EvenSecondException("Even second exception: " + LocalDateTime.now());
        }
        return message;
    }
}
