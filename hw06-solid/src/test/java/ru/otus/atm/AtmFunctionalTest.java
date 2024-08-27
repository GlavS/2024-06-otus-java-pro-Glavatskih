package ru.otus.atm;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.atm.noteholder.BanknoteHolderException;
import ru.otus.atm.noteholder.rf.RoubleBanknoteHolder;
import ru.otus.atm.processor.AmountConverterImpl;
import ru.otus.atm.processor.ProcessorImpl;
import ru.otus.banknote.Banknote;
import ru.otus.banknote.rf.*;

class AtmFunctionalTest {
    private static final List<Banknote> banknotes = new ArrayList<>();
    private static final int ATM_INITIAL_LOAD = 12200;
    private Atm atm;

    @BeforeAll
    static void setUp() {
        IntStream.range(0, 3).forEach(i -> banknotes.add(new Roubles100()));
        IntStream.range(0, 2).forEach(i -> banknotes.add(new Roubles200()));
        IntStream.range(0, 3).forEach(i -> banknotes.add(new Roubles500()));
        IntStream.range(0, 3).forEach(i -> banknotes.add(new Roubles1000()));
        banknotes.add(new Roubles2000());
        banknotes.add(new Roubles5000());
    }

    @BeforeEach
    void initEach() {
        var holder = new RoubleBanknoteHolder();
        var converter = new AmountConverterImpl(holder);
        var processor = new ProcessorImpl(holder, converter);
        atm = new AtmService(processor);
        atm.accept(banknotes);
    }

    @Test
    @DisplayName("Банкомат должен отображать баланс")
    void balanceMethodShouldReturnTotalBalance() {
        assertThat(atm.balance()).isEqualTo(ATM_INITIAL_LOAD);
    }

    @Test
    @DisplayName("Функция выдачи банкнот должна работать")
    void dispenseMethodIsWorking() {
        List<Banknote> hundredExpected = atm.dispense(100);
        assertThat(hundredExpected).isNotEmpty().hasSize(1);
        assertThat(hundredExpected).filteredOn(h -> h instanceof Roubles100).hasSize(1);
    }

    @Test
    @DisplayName("Баланс должен быть актуальным")
    void balanceIsCalculatedCorrectly() {
        assertThat(atm.balance()).isEqualTo(ATM_INITIAL_LOAD);
        atm.dispense(100);
        assertThat(atm.balance()).isEqualTo(ATM_INITIAL_LOAD - 100);
    }

    @Test
    @DisplayName("Функция выдачи банкнот должна выдавать корректную сумму")
    void dispenseMethodShouldReturnCorrectBanknotes() {
        List<Banknote> banknoteList = atm.dispense(4400);

        assertThat(banknoteList).isNotEmpty().hasSize(5);
        assertThat(banknoteList).filteredOn(b -> b instanceof Roubles2000).hasSize(1);
        assertThat(banknoteList).filteredOn(b -> b instanceof Roubles1000).hasSize(2);
        assertThat(banknoteList).filteredOn(b -> b instanceof Roubles200).hasSize(2);
    }

    @Test
    @DisplayName("Функция приема банкнот должна работать")
    void acceptMethodShouldWorkCorrectly() {
        List<Banknote> forAccept = List.of(new Roubles200(), new Roubles500());
        atm.accept(forAccept);
        assertThat(atm.balance()).isEqualTo(ATM_INITIAL_LOAD + 200 + 500);
    }

    @Test
    @DisplayName("Функция выдачи должна бросать исключение, если необходимых банкнот нет")
    void dispenseMethodShouldThrowCorrectExceptionIfThereAreNoSuitableBanknotes() {
        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atm.dispense(ATM_INITIAL_LOAD + 100))
                .withMessage("Requested quantity cannot be dispensed")
                .havingCause()
                .isInstanceOf(BanknoteHolderException.class)
                .withMessage("Cannot dispense requested amount of 12300");
    }

    @Test
    @DisplayName("Функция выдачи должна бросать исключение, если запрошенная сумма некорректна")
    void dispenseMethodShouldThrowCorrectExceptionIfRequestedAmountIsIncorrect() {
        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atm.dispense(50))
                .withMessage("Requested quantity cannot be dispensed")
                .havingCause()
                .isInstanceOf(BanknoteHolderException.class)
                .withMessage("Requested quantity should be a multiple of 100");
    }
}
