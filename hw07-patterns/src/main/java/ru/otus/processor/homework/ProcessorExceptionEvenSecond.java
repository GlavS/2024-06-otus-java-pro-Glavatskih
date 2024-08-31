package ru.otus.processor.homework;

import java.time.LocalDateTime;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorExceptionEvenSecond implements Processor {
    @Override
    public Message process(Message message) {
        if (LocalDateTime.now().getSecond() % 2 == 0) {
            throw new EvenSecondException("Even second exception: " + LocalDateTime.now());
        }
        return message;
    }
}
