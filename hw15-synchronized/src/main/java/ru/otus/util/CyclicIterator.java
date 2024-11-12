package ru.otus.util;

import java.util.concurrent.atomic.AtomicInteger;

public class CyclicIterator {
    private final AtomicInteger count = new AtomicInteger(0);
    private boolean ascending = true;

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
}
