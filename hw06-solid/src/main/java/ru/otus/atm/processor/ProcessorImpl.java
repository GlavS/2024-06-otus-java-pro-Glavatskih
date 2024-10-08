package ru.otus.atm.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.atm.noteholder.BanknoteHolderException;
import ru.otus.atm.noteholder.NoteHolder;
import ru.otus.banknote.Banknote;

public class ProcessorImpl implements Processor {
    private final NoteHolder noteHolder;
    private final AmountConverter amountConverter;

    public ProcessorImpl(NoteHolder noteHolder, AmountConverter amountConverter) {
        this.noteHolder = noteHolder;
        this.amountConverter = amountConverter;
    }

    @Override
    public List<Banknote> dispenseNotes(int amount) {
        var requestBanknotesList = amountConverter.calculate(amount);
        List<Banknote> result = new ArrayList<>();
        for (CellRequest request : requestBanknotesList) {
            int quantity = request.quantity();
            for (int i = 0; i < quantity; i++) {
                Optional<Banknote> banknote = noteHolder.getFromCell(request.nominal());
                result.add(banknote.orElseThrow(() -> new BanknoteHolderException("Cannot find banknote to dispense")));
            }
        }
        return result;
    }

    @Override
    public void acceptNotes(List<Banknote> banknoteList) {
        for (Banknote b : banknoteList) {
            noteHolder.addToCell(b);
        }
    }

    @Override
    public int getTotalInfo() {
        return noteHolder.totalValue();
    }
}
