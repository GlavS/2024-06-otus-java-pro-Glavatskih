package ru.otus.listener.homework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

class HistoryListenerTest {

    @Test
    void listenerTest() {
        // given
        var historyListener = new HistoryListener(LocalDateTime::now);

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        var message =
                new Message.Builder(id).field10("field10").field13(field13).build();

        // when
        historyListener.onUpdated(message);
        message.getField13().setData(new ArrayList<>()); // меняем исходное сообщение
        field13Data.clear(); // меняем исходный список

        // then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
        assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
    }

    @Test
    void listenerShouldNotThrowNPEIfField13IsNull() {
        // given
        var historyListener = new HistoryListener(LocalDateTime::now);
        // when
        var id = 10L;
        var message = new Message.Builder(id).build();
        // then
        assertThatNoException().isThrownBy(() -> historyListener.onUpdated(message));
    }
}
