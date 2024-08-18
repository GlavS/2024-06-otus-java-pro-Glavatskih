package ru.otus.atm.processor;

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

        validateInputAmount(requestedAmount, nominals);

        List<CellRequest> requestList = calculateCellRequests(requestedAmount, nominals, noteHolder);

        validateCalculatedAmount(requestedAmount, requestList);

        return requestList;
    }

    private static void validateInputAmount(int amount, Set<Integer> nominals) {
        int minNominal = nominals.stream()
                .min(Integer::compareTo)
                .orElseThrow(
                        () -> new BanknoteHolderException("Banknote holder does not provide required nominal list"));
        if (amount % minNominal != 0 || amount < minNominal) {
            throw new BanknoteHolderException("Requested quantity should be a multiple of " + minNominal);
        }
    }

    private static List<CellRequest> calculateCellRequests(int amount, Set<Integer> nominals, NoteHolder noteHolder) {
        List<CellRequest> requestList = new ArrayList<>();
        for (Integer nominal : nominals) {
            int quantity = amount / nominal;
            int banknotesAvailable = noteHolder.cellBanknoteCount(nominal);
            if (banknotesAvailable < quantity) {
                quantity = banknotesAvailable;
            }
            requestList.add(new CellRequest(nominal, quantity));
            amount -= quantity * nominal;
        }
        return requestList;
    }

    private static void validateCalculatedAmount(int amount, List<CellRequest> requestList) {
        int calculatedAmount = requestList.stream()
                .map(cellRequest -> cellRequest.nominal() * cellRequest.quantity())
                .reduce(Integer::sum)
                .orElse(0);
        if (calculatedAmount != amount) {
            throw new BanknoteHolderException("Cannot dispense requested amount of " + amount);
        }
    }
}
