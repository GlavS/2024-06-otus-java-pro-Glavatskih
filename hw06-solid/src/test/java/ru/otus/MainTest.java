package ru.otus;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class MainTest {

    @Test
    void mainMethodRunsWithNoError() {
        assertThatNoException().isThrownBy(() -> Main.main(any()));
    }
}