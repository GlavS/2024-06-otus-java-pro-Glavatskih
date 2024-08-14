package ru.otus.banknote.rf;

import ru.otus.banknote.Banknote;

public final class Roubles100 implements Banknote, Rouble {
    @Override
    public int value() {
        return 100;
    }

    @Override
    public String toString() {
        return "Roubles 100";
    }
}
