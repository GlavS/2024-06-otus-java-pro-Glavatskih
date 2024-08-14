package ru.otus;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void mainMethodRunsWithNoError() {
        assertThatNoException().isThrownBy(() -> Main.main(any()));
    }
}
