package ru.otus.atm;

import java.util.List;
import ru.otus.banknote.Banknote;

public interface Atm {
    void accept(List<Banknote> banknoteList);

    List<Banknote> dispense(int amount);

    int balance();
}
