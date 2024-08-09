package ru.otus.testclasses;

import lombok.extern.slf4j.Slf4j;
import ru.otus.annotation.Log;

@Slf4j
public class SecureInterfaceImpl implements SecureInterface {
    @Override
    public void securePrint(String info) {
        log.info("This is secure: {}", info);
    }

    @Override
    public void insecurePrint(String info) {
        log.info("This has to be logged: {}", info);
    }

    @Override
    @Log
    public void insecurePrint(String info, int index) {
        log.info("This has to be logged: {}, index {}", info, index);
    }
}
