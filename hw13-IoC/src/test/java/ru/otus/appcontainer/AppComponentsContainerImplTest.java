package ru.otus.appcontainer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;

@Disabled
class AppComponentsContainerImplTest {

    @Test
    void mainTest() {
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
    }
}
