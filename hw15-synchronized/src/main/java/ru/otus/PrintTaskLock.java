package ru.otus;

import static ru.otus.util.SleepUtil.sleep;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.util.CyclicIterator;

public class PrintTaskLock implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PrintTaskLock.class);
    private final Lock lock = new ReentrantLock();
    private final Condition lastThread = lock.newCondition();
    private String lastWorkingThreadName = "Thread 2";

    @Override
    public void run() {
        CyclicIterator iterator = new CyclicIterator();
        lock.lock();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    while (lastWorkingThreadName.equals(Thread.currentThread().getName())) {
                        lastThread.await();
                    }
                    logger.info("[{}] - {}", Thread.currentThread().getName(), iterator.next());
                    lastWorkingThreadName = Thread.currentThread().getName();
                    sleep();
                    lastThread.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
