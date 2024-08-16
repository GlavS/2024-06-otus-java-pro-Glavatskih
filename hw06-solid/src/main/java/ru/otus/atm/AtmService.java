package ru.otus.atm;

import java.util.List;
import ru.otus.atm.dispenser.Processor;
import ru.otus.atm.noteholder.BanknoteHolderException;
import ru.otus.banknote.Banknote;

public class AtmService implements Atm {

    private final Processor processor;

    public AtmService(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void accept(List<Banknote> banknoteList) {
        processor.acceptNotes(banknoteList);
    }

    @Override
    public List<Banknote> dispense(int amount) {
        try {
            return processor.dispenseNotes(amount);
        } catch (BanknoteHolderException e) {
            throw new AtmException("Requested amount cannot be dispensed", e);
        }
    }

    @Override
    public int balance() {
        return processor.getTotalInfo();
    }
}
