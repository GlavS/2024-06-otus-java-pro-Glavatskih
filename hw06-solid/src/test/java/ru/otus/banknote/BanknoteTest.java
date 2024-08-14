package ru.otus.banknote;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.banknote.rf.*;

class BanknoteTest {

    @Test
    @DisplayName("Банкноты должны возвращать свой номинал")
    void banknotesShouldReturnCorrectValueNominal() {
        List<Integer> values = new ArrayList<>();
        Banknote b100 = new Roubles100();
        Banknote b200 = new Roubles200();
        Banknote b500 = new Roubles500();
        Banknote b1000 = new Roubles1000();
        Banknote b2000 = new Roubles2000();
        Banknote b5000 = new Roubles5000();

        values.add(b100.value());
        values.add(b200.value());
        values.add(b500.value());
        values.add(b1000.value());
        values.add(b2000.value());
        values.add(b5000.value());

        assertThat(values).containsExactly(100, 200, 500, 1000, 2000, 5000);
    }
}
