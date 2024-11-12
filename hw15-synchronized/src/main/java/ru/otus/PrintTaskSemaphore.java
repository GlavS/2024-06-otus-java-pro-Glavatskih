package ru.otus;

import static ru.otus.util.SleepUtil.sleep;

import java.util.concurrent.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.util.CyclicIterator;

public class PrintTaskSemaphore implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PrintTaskSemaphore.class);
    private final Semaphore semaphore = new Semaphore(0, true);
    private String lastWorkingThreadName = "Thread 2";

    @Override
    public void run() {
        CyclicIterator iterator = new CyclicIterator();

        while (!Thread.currentThread().isInterrupted()) {
            while (Thread.currentThread().getName().equals(lastWorkingThreadName)) {
                semaphore.acquireUninterruptibly();
            }
            logger.info("[{}] - {}", Thread.currentThread().getName(), iterator.next());
            sleep();
            lastWorkingThreadName = Thread.currentThread().getName();
            semaphore.release();
        }
    }
}
