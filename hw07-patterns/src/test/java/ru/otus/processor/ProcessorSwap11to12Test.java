package ru.otus.processor;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

class ProcessorSwap11to12Test {
    @Test
    public void swapTest() {
        ProcessorSwap11to12 processorSwap11to12 = new ProcessorSwap11to12();
        Message message =
                new Message.Builder(1L).field11("Field11").field12("Field12").build();
        Message expected =
                new Message.Builder(1L).field11("Field12").field12("Field11").build();
        Message processed = processorSwap11to12.process(message);
        assertThat(processed).usingRecursiveComparison().isEqualTo(expected);
    }
}
