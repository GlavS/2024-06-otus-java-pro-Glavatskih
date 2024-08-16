package ru.otus.atm.dispenser;

import java.util.List;
import ru.otus.banknote.Banknote;

public interface Processor {
    List<Banknote> dispenseNotes(int amount);

    void acceptNotes(List<Banknote> banknoteList);

    int getTotalInfo();
}
