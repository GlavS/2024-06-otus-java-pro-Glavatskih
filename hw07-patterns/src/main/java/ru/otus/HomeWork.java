package ru.otus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.EvenSecondException;
import ru.otus.processor.homework.ProcessorExceptionEvenSecond;
import ru.otus.processor.homework.ProcessorSwap11to12;

@SuppressWarnings({"java:S125", "java:S106", "java:S1144"})
public class HomeWork {

    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);
    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    public static void main(String[] args) {
        /*
          по аналогии с Demo.class
          из элеменов "to do" создать new ComplexProcessor и обработать сообщение
        */
        var processorList = List.of(new ProcessorSwap11to12(), new ProcessorExceptionEvenSecond(LocalDateTime::now));
        Consumer<Exception> exceptionHandler = exception -> {
            if (exception instanceof EvenSecondException) {
                logger.error("EvenSecondException happened", exception);
            }
        };
        var mainProcessor = new ComplexProcessor(processorList, exceptionHandler);
        var historyListener = new HistoryListener(LocalDateTime::now);
        mainProcessor.addListener(historyListener);
        ObjectForMessage ofm = new ObjectForMessage();
        ofm.setData(List.of("Hello World", "Goodbye World", "42"));

        Message message = new Message.Builder(1L)
                .field11("Number 11")
                .field12("Number 12")
                .field13(ofm)
                .build();

        // Раскомментировать для гарантированного получения исключения EvenSecondException
        // waitUntilEvenSecond();

        Message result = mainProcessor.handle(message);
        System.out.println();
        for (int i = 0; i < 4; i++) {
            result = mainProcessor.handle(result);
            System.out.println();
        }

        logger.info("Result message: {}", result);
        mainProcessor.removeListener(historyListener);
    }

    private static void waitUntilEvenSecond() {
        int currentSecond = LocalDateTime.now().getSecond();
        if (currentSecond % 2 == 1) {
            waitOneSecond();
        }
    }

    private static void waitOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
