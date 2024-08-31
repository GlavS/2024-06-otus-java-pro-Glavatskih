package ru.otus.processor;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Message;

class ProcessorExceptionEvenSecondStartTest {

    Logger logger = LoggerFactory.getLogger(ProcessorExceptionEvenSecondStartTest.class);

    @Test
    void processorShouldThrowExceptionIfCalledOnEvenSecond() {
        // given
        Deque<ExceptionLog> exceptionLog = new ArrayDeque<>();
        ProcessorExceptionEvenSecondStart processor = new ProcessorExceptionEvenSecondStart();
        Message emptyMsg = new Message.Builder(1L).build();
        // when
        logger.info("Starting test, wait for 12 sec...");
        while (exceptionLog.size() < 12) {
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
                .filter(el -> el.second() % 2 == 0)
                .filter(ExceptionLog::exceptionWasThrown)
                .count();
        long runsWithNoExceptions = exceptionLog.stream()
                .filter(el -> el.second() % 2 == 1)
                .filter(el -> !el.exceptionWasThrown())
                .count();
        assertThat(exceptionsExpected).isEqualTo(6);
        assertThat(runsWithNoExceptions).isEqualTo(6);
    }

    private static void waitOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private record ExceptionLog(int second, boolean exceptionWasThrown) {}
}
