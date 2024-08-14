package ru.otus.atm.noteholder;

import ru.otus.banknote.Banknote;

import java.util.Optional;
import java.util.Set;

public interface NoteHolder {
    void addToCell(Banknote banknote);

    Optional<Banknote> getFromCell(int nominal);

    int totalValue();

    Set<Integer> getAllowedAmounts();
}
