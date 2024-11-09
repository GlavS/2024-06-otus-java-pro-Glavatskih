package ru.otus.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CyclicIterator {
    private final AtomicInteger count = new AtomicInteger(0);
    private boolean ascending = true;

    public CyclicIterator() {}

    public int next() {
        int result;
        if (ascending) {
            result = count.incrementAndGet();
            if (result == 10) {
                ascending = false;
            }
        } else {
            result = count.decrementAndGet();
            if (result == 1) {
                ascending = true;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CyclicIterator iterator = new CyclicIterator();
        for (int i = 0; i < 30; i++) {
            System.out.println(iterator.next());
        }
    }
}
