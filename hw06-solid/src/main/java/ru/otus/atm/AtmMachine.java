package ru.otus.atm;

import ru.otus.atm.noteholder.NoteHolder;
import ru.otus.banknote.Banknote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AtmMachine implements Atm {

    private final NoteHolder noteHolder;

    public AtmMachine(NoteHolder noteHolder) {
        this.noteHolder = noteHolder;
    }

    @Override
    public void accept(List<Banknote> banknoteList) {
        for (Banknote b : banknoteList) {
            noteHolder.addToCell(b);
        }
    }

    @Override
    public List<Banknote> dispense(int amount) {
        int amountRequested = amount;
        Set<Integer> nominals = noteHolder.getAllowedAmounts();
        List<Request> requestList = new ArrayList<>();
        for (Integer nominal : nominals) {
            int quantity = amountRequested / nominal;
            requestList.add(new Request(nominal, quantity));
            amountRequested -= quantity * nominal;
        }
        List<Banknote> result = new ArrayList<>();
        for (Request request : requestList) {
            int quantity = request.amount();
            for(int i = 0; i < quantity; i ++){
                Optional<Banknote> banknote = noteHolder.getFromCell(request.nominal());
                banknote.ifPresent(result::add);
                banknote.orElseThrow(() -> new RuntimeException("Cannot find banknote to dispense"));
            }
        }
        if(amountRequested != 0) {
            throw new RuntimeException("Cannot dispense amount of " + amount + " roubles");
        }
        return result;
    }

    @Override
    public int balance() {
        return noteHolder.totalValue();
    }

    private static record Request(int nominal, int amount){}
}
