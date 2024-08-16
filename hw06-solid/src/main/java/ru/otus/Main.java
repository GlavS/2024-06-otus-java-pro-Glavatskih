package ru.otus;

import java.util.ArrayList;
import java.util.List;
import ru.otus.atm.Atm;
import ru.otus.atm.AtmService;
import ru.otus.atm.dispenser.AmountConverter;
import ru.otus.atm.dispenser.AmountConverterImpl;
import ru.otus.atm.dispenser.Processor;
import ru.otus.atm.dispenser.ProcessorImpl;
import ru.otus.atm.noteholder.NoteHolder;
import ru.otus.atm.noteholder.rf.RoubleBanknoteHolder;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.rf.Roubles100;
import ru.otus.banknote.rf.Roubles1000;
import ru.otus.banknote.rf.Roubles2000;
import ru.otus.banknote.rf.Roubles500;

public class Main {
    public static void main(String[] args) {
        NoteHolder holder = new RoubleBanknoteHolder();
        AmountConverter converter = new AmountConverterImpl(holder);
        Processor processor = new ProcessorImpl(holder, converter);
        Atm bankomat = new AtmService(processor);
        List<Banknote> pachka = new ArrayList<>();
        pachka.add(new Roubles2000());
        pachka.add(new Roubles1000());
        pachka.add(new Roubles500());
        pachka.add(new Roubles100());

        bankomat.accept(pachka);

        System.out.println(bankomat.balance());

        List<Banknote> dispense = bankomat.dispense(1000);
        System.out.println(dispense);
    }
}
