package ru.otus.atm.noteholder.rf;

import java.util.*;
import ru.otus.atm.noteholder.BanknoteHolderException;
import ru.otus.atm.noteholder.NoteHolder;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.rf.Rouble;

public final class RoubleBanknoteHolder implements NoteHolder {

    private final Map<Integer, Deque<Banknote>> cells = new HashMap<>();
    private static final Set<Integer> AMOUNTS_ALLOWED;

    static {
        AMOUNTS_ALLOWED = new TreeSet<>(Comparator.reverseOrder());
        AMOUNTS_ALLOWED.add(100);
        AMOUNTS_ALLOWED.add(200);
        AMOUNTS_ALLOWED.add(500);
        AMOUNTS_ALLOWED.add(1000);
        AMOUNTS_ALLOWED.add(2000);
        AMOUNTS_ALLOWED.add(5000);
    }

    @Override
    public void addToCell(Banknote banknote) {
        if (!(banknote instanceof Rouble)) {
            throw new BanknoteHolderException("Wrong banknote country");
        }
        Deque<Banknote> init = new ArrayDeque<>();
        init.push(banknote);
        cells.merge(banknote.value(), init, (oldDeq, unused) -> {
            oldDeq.push(banknote);
            return oldDeq;
        });
    }

    @Override
    public Optional<Banknote> getFromCell(int nominal) {
        var deque = cells.get(nominal);
        return (deque == null || deque.isEmpty()) ? Optional.empty() : Optional.of(deque.pop());
    }

    @Override
    public int cellBanknoteCount(int nominal) {
        return cells.get(nominal).size();
    }

    @Override
    public int totalValue() {
        return cells.keySet().stream()
                .map(nominal -> nominal * cellBanknoteCount(nominal))
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public Set<Integer> getAllowedAmounts() {
        return AMOUNTS_ALLOWED;
    }
}
