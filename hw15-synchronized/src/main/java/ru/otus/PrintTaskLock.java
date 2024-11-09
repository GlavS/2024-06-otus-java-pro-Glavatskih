package ru.otus;

import ru.otus.util.CyclicIterator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.otus.util.SleepUtil.sleep;

public class PrintTaskLock implements Runnable {
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
                    System.out.printf("[%s] - %d%n", Thread.currentThread().getName(), iterator.next());
                    if (Thread.currentThread().getName().equals("Thread 2")) {
                        System.out.println();
                    }
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
