package ru.otus.atm.dispenser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ru.otus.atm.noteholder.BanknoteHolderException;
import ru.otus.atm.noteholder.NoteHolder;

public class AmountConverterImpl implements AmountConverter {
    private final NoteHolder noteHolder;

    public AmountConverterImpl(NoteHolder noteHolder) {
        this.noteHolder = noteHolder;
    }

    @Override
    public List<CellRequest> calculate(int requestedAmount) {
        Set<Integer> nominals = noteHolder.getAllowedAmounts();
        int minNominal = nominals.stream()
                .min(Integer::compareTo)
                .orElseThrow(
                        () -> new BanknoteHolderException("Banknote holder does not provide required nominal list"));
        if (requestedAmount % minNominal != 0) {
            throw new BanknoteHolderException("Requested amount should be a multiple of " + minNominal);
        }
        List<CellRequest> requestList = new ArrayList<>();
        for (Integer nominal : nominals) {
            int quantity = requestedAmount / nominal;
            requestList.add(new CellRequest(nominal, quantity));
            requestedAmount -= quantity * nominal;
        }
        return requestList;
    }
}
