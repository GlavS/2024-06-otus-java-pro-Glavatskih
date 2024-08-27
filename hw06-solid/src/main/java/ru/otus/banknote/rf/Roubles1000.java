package ru.otus.banknote.rf;

import ru.otus.banknote.Banknote;

public final class Roubles1000 implements Banknote, Rouble {
    @Override
    public int value() {
        return 1000;
    }

    @Override
    public String toString() {
        return "Roubles 1000";
    }
}
