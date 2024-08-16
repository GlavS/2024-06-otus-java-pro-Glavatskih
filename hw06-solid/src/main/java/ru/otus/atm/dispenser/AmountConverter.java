package ru.otus.atm.dispenser;

import java.util.List;

public interface AmountConverter {
    List<CellRequest> calculate(int amount);
}
