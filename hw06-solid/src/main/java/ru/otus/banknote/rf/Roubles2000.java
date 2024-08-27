package ru.otus.banknote.rf;

import ru.otus.banknote.Banknote;

public final class Roubles2000 implements Banknote, Rouble {
    @Override
    public int value() {
        return 2000;
    }

    @Override
    public String toString() {
        return "Roubles 2000";
    }
}
