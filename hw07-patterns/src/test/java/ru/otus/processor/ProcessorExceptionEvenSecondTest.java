package ru.otus.processor;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Message;

class ProcessorExceptionEvenSecondTest {
    private static final int TEST_RUNS = 12;
    private static final int RUNS_WITH_EXCEPTION = TEST_RUNS / 2;
    private static final int RUNS_WITH_NO_EXCEPTION = TEST_RUNS / 2;

    Logger logger = LoggerFactory.getLogger(ProcessorExceptionEvenSecondTest.class);

    @Test
    @DisplayName("Процессор должен бросать заданный тип исключения")
    void processorShouldThrowCorrectException() {
        // given
        Exception exception = null;
        ProcessorExceptionEvenSecond processor = new ProcessorExceptionEvenSecond();
        Message emptyMsg = new Message.Builder(1L).build();
        // when
        while (exception == null) {
            waitOneSecond();
            try {
                processor.process(emptyMsg);
            } catch (EvenSecondException e) {
                exception = e;
            }
        }
        // then
        assertThat(exception).isInstanceOf(EvenSecondException.class);
    }

    @Test
    @DisplayName("Процессор должен бросать исключение только будучи вызванным в четную секунду")
    void processorShouldThrowExceptionIfCalledOnEvenSecond() {
        // given
        Deque<ExceptionLog> exceptionLog = new ArrayDeque<>();
        ProcessorExceptionEvenSecond processor = new ProcessorExceptionEvenSecond();
        Message emptyMsg = new Message.Builder(1L).build();
        // when
        logger.info("Starting test, wait for 12 sec...");
        while (exceptionLog.size() < TEST_RUNS) {
            waitOneSecond();
            try {
                processor.process(emptyMsg);
                exceptionLog.push(new ExceptionLog(LocalDateTime.now().getSecond(), false));
            } catch (EvenSecondException e) {
                exceptionLog.push(new ExceptionLog(LocalDateTime.now().getSecond(), true));
            }
            logger.info("Processor invocation {}", exceptionLog.size());
        }
        logger.info("Done.");
        // then
        long exceptionsExpected = exceptionLog.stream()
                .filter(el -> isEven(el.second()))
                .filter(ExceptionLog::exceptionWasThrown)
                .count();
        long runsWithNoExceptions = exceptionLog.stream()
                .filter(el -> isOdd(el.second()))
                .filter(el -> !el.exceptionWasThrown())
                .count();
        assertThat(exceptionsExpected).isEqualTo(RUNS_WITH_EXCEPTION);
        assertThat(runsWithNoExceptions).isEqualTo(RUNS_WITH_NO_EXCEPTION);
    }

    private static void waitOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static boolean isOdd(int number) {
        return number % 2 == 1;
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    private record ExceptionLog(int second, boolean exceptionWasThrown) {}
}
