package ru.otus.banknote.rf;

import ru.otus.banknote.Banknote;

public final class Roubles200 implements Banknote, Rouble {
    @Override
    public int value() {
        return 200;
    }

    @Override
    public String toString() {
        return "Roubles 200";
    }
}
