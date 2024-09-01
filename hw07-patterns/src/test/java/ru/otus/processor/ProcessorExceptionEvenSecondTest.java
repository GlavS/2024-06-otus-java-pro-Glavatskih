package ru.otus.processor;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("Процессор должен бросать заданное исключение только будучи вызванным в четную секунду")
    void processorShouldThrowExceptionIfCalledOnEvenSecond() {
        for (int i = 0; i < 60; i += 2) {
            // when
            Mockito.when(timeProviderMock.getTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, i)));
            // then
            assertThatThrownBy(() -> processor.process(emptyMsg)).isInstanceOf(EvenSecondException.class);
        }
        for (int i = 1; i < 60; i += 2) {
            // when
            Mockito.when(timeProviderMock.getTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, i)));
            // then
            assertThatNoException().isThrownBy(() -> processor.process(emptyMsg));
        }
    }
}
