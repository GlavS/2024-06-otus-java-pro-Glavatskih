package ru.otus;

import static ru.otus.util.SleepUtil.sleep;

import ru.otus.util.CyclicIterator;

public class PrintTaskSynchronized implements Runnable {
    private String lastWorkingThreadName = "Thread 2";

    @Override
    public synchronized void run() {
        CyclicIterator iterator = new CyclicIterator();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (lastWorkingThreadName.equals(Thread.currentThread().getName())) {
                    this.wait();
                }
                System.out.printf("[%s] - %d%n", Thread.currentThread().getName(), iterator.next());
                if (Thread.currentThread().getName().equals("Thread 2")) {
                    System.out.println();
                }
                lastWorkingThreadName = Thread.currentThread().getName();
                sleep();
                this.notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
