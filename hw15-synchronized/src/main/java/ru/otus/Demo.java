package ru.otus;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        PrintTaskSynchronized printTaskSynchronized = new PrintTaskSynchronized();
        PrintTaskLock printTaskLock = new PrintTaskLock();
        PrintTaskSemaphore printTaskSemaphore = new PrintTaskSemaphore();
        //                        Thread thread1 = new Thread(printTaskSynchronized, "Thread 1");
        //                        Thread thread2 = new Thread(printTaskSynchronized, "Thread 2");
        //        Thread thread1 = new Thread(printTaskLock, "Thread 1");
        //        Thread thread2 = new Thread(printTaskLock, "Thread 2");
        Thread thread1 = new Thread(printTaskSemaphore, "Thread 1");
        Thread thread2 = new Thread(printTaskSemaphore, "Thread 2");

        thread1.start();
        thread2.start();
    }
}
