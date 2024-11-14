package ru.otus.util;

public class CyclicIterator {
    private int counter;
    private boolean ascending = true;

    public int next() {
        if (ascending) {
            counter++;
            if (counter == 10) {
                ascending = false;
            }
        } else {
            counter--;
            if (counter == 1) {
                ascending = true;
            }
        }
        return counter;
    }
}
