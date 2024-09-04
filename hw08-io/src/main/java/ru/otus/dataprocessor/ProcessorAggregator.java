package ru.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        if (data == null) {
            throw new IllegalArgumentException("Processor data is null");
        }
        return data.stream()
                .collect(Collectors.groupingBy(Measurement::name, Collectors.summingDouble(Measurement::value)));
    }
}