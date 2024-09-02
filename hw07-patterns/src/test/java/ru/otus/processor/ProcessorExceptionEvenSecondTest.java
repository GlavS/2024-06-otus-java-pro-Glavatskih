package ru.otus.processor;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.otus.model.Message;
import ru.otus.processor.homework.EvenSecondException;
import ru.otus.processor.homework.ProcessorExceptionEvenSecond;
import ru.otus.timeprovider.TimeProvider;

class ProcessorExceptionEvenSecondTest {

    private final TimeProvider timeProviderMock = Mockito.mock(TimeProvider.class);
    private final ProcessorExceptionEvenSecond processor = new ProcessorExceptionEvenSecond(timeProviderMock);
    private final Message emptyMsg = new Message.Builder(1L).build();
    private final LocalDate date = LocalDate.MIN;

    @Test
    @DisplayName("Процессор должен бросать заданный тип исключения")
    void processorShouldThrowCorrectException() {
        // when
        Mockito.when(timeProviderMock.getTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, 2)));
        // then
        assertThatThrownBy(() -> processor.process(emptyMsg)).isInstanceOf(EvenSecondException.class);
    }

    @ParameterizedTest(name = "Процессор должен бросить исключение на {0} секунде")
    @MethodSource("evenSecondsGenerator")
    @DisplayName("Процессор должен бросать заданное исключение только будучи вызванным в четную секунду")
    void processorShouldThrowExceptionIfCalledOnEvenSecond(int second) {
        // when
        Mockito.when(timeProviderMock.getTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, second)));
        // then
        assertThatThrownBy(() -> processor.process(emptyMsg)).isInstanceOf(EvenSecondException.class);
    }

    @ParameterizedTest(name = "Процессор не должен бросать исключение на {0} секунде")
    @MethodSource("oddSecondsGenerator")
    @DisplayName("Процессор не должен бросать исключение в нечетную секунду")
    void processorShouldNotThrowExceptionIfCalledOnOddSecond(int second) {
        // when
        Mockito.when(timeProviderMock.getTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, second)));
        // then
        assertThatNoException().isThrownBy(() -> processor.process(emptyMsg));
    }

    private static IntStream evenSecondsGenerator() {
        return IntStream.range(0, 60).filter(i -> i % 2 == 0);
    }

    private static IntStream oddSecondsGenerator() {
        return IntStream.range(0, 60).filter(i -> i % 2 == 1);
    }
}
