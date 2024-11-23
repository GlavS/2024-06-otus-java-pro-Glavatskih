package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SampleMessage message = SampleMessage.newBuilder()
                .setName("Pushkin")
                .setAge(37)
                .setMale(true)
                .build();
        logger.info("Sample message : \n{}", message);
    }
}
