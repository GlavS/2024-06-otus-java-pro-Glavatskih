package ru.otus;

import static ru.otus.util.SleepUtil.sleep;

import java.util.concurrent.Semaphore;
import ru.otus.util.CyclicIterator;

public class PrintTaskSemaphore implements Runnable {
    private final Semaphore semaphore = new Semaphore(0, true);
    private String lastWorkingThreadName = "Thread 2";

    @Override
    public void run() {
        CyclicIterator iterator = new CyclicIterator();

        while (!Thread.currentThread().isInterrupted()) {
            while (Thread.currentThread().getName().equals(lastWorkingThreadName)) {
                semaphore.acquireUninterruptibly();
            }
            System.out.printf("[%s] - %d%n", Thread.currentThread().getName(), iterator.next());
            if (Thread.currentThread().getName().equals("Thread 2")) {
                System.out.println();
            }
            sleep();
            lastWorkingThreadName = Thread.currentThread().getName();
            semaphore.release();
        }
    }
}
