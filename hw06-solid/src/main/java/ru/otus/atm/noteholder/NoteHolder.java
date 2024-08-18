package ru.otus.atm.noteholder;

import java.util.Optional;
import java.util.Set;
import ru.otus.banknote.Banknote;

public interface NoteHolder {
    void addToCell(Banknote banknote);

    Optional<Banknote> getFromCell(int nominal);

    int totalValue();

    int cellBanknoteCount(int nominal);

    Set<Integer> getAllowedAmounts();
}
