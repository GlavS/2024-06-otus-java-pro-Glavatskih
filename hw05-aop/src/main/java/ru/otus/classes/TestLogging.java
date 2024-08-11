package ru.otus.classes;

import lombok.extern.slf4j.Slf4j;
import ru.otus.annotation.Log;

@Slf4j
public class TestLogging implements TestLoggingInterface {

    @Override
    public void calculate(String info) {
        log.info("Test calculation info: {}", info);
    }

    @Override
    @Log
    public void calculate(int first, String info) {
        log.info("Calculation {}, one parameter: {}", info, first);
    }

    @Override
    public void calculate(int first, int second, String info) {
        log.info("Calculation {}, two parameters: {} and {}", info, first, second);
    }
}
