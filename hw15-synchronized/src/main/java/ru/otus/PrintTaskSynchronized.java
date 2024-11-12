package ru.otus;

import static ru.otus.util.SleepUtil.sleep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.util.CyclicIterator;

public class PrintTaskSynchronized implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PrintTaskSynchronized.class);
    private String lastWorkingThreadName = "Thread 2";

    @Override
    public synchronized void run() {
        final CyclicIterator iterator = new CyclicIterator();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (lastWorkingThreadName.equals(Thread.currentThread().getName())) {
                    this.wait();
                }
                logger.info("[{}] - {}", Thread.currentThread().getName(), iterator.next());
                lastWorkingThreadName = Thread.currentThread().getName();
                sleep();
                this.notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
