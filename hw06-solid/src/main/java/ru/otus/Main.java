package ru.otus;

import java.util.ArrayList;
import java.util.List;
import ru.otus.atm.Atm;
import ru.otus.atm.AtmMachine;
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
        Atm bankomat = new AtmMachine(holder);
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
