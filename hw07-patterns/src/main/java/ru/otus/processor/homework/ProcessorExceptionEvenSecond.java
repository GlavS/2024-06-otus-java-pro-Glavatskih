package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.timeprovider.TimeProvider;

public class ProcessorExceptionEvenSecond implements Processor {
    private final TimeProvider timeProvider;

    public ProcessorExceptionEvenSecond(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        if (timeProvider.getTime().getSecond() % 2 == 0) {
            throw new EvenSecondException("Even second exception: " + timeProvider.getTime());
        }
        return message;
    }
}
