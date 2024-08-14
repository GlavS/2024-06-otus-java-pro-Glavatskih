package ru.otus.banknote.rf;

import ru.otus.banknote.Banknote;

public final class Roubles500 implements Banknote, Rouble {
    @Override
    public int value() {
        return 500;
    }
    @Override
    public String toString() {
        return "Roubles 500";
    }
}
