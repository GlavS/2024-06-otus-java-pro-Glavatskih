package ru.otus.atm.noteholder.rf;

import java.util.*;
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
            throw new WrongCurrencyException("Wrong banknote country");
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
        //        return Optional.ofNullable(cells.get(nominal).pop());
        return cells.get(nominal) == null
                ? Optional.empty()
                : Optional.of(cells.get(nominal).pop());
    }

    @Override
    public int totalValue() {
        return cells.entrySet().stream()
                .map(entry -> entry.getKey() * entry.getValue().size())
                .reduce(0, Integer::sum);
    }

    @Override
    public Set<Integer> getAllowedAmounts() {
        return AMOUNTS_ALLOWED;
    }
}
