package ru.otus.atm.noteholder.rf;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.Atm;
import ru.otus.atm.AtmMachine;
import ru.otus.atm.noteholder.NoteHolder;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.rf.Roubles500;

class RoubleBanknoteHolderTest {
    private final NoteHolder holder = new RoubleBanknoteHolder();
    private final Atm atm = new AtmMachine(holder);

    @BeforeEach
    void init() {
        Banknote b500 = new Roubles500();
        holder.addToCell(b500);
        holder.addToCell(b500);
        holder.addToCell(b500);
    }

    @Test
    void addToCellMethodShouldCorrectlyAddBanknotes() {
        assertThat(atm.balance()).isEqualTo(1500);
    }

    @Test
    void getFromCell() {}

    @Test
    void getQuantityOfCell() {}
}
