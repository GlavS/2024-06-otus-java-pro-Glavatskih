package ru.otus.atm.processor;

import java.util.List;

public interface AmountConverter {
    List<CellRequest> calculate(int amount);
}
