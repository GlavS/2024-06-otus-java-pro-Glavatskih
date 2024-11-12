package ru.otus.util;

import java.util.concurrent.TimeUnit;

public class SleepUtil {

    private SleepUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
