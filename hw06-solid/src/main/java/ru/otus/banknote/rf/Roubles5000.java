package ru.otus.banknote.rf;

import ru.otus.banknote.Banknote;

public final class Roubles5000 implements Banknote, Rouble {
    @Override
    public int value() {
        return 5000;
    }

    @Override
    public String toString() {
        return "Roubles 5000";
    }
}
