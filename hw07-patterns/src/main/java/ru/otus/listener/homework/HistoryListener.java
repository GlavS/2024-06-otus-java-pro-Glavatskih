package ru.otus.listener.homework;

import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.timeprovider.TimeProvider;

public class HistoryListener implements Listener, HistoryReader {
    private static final Logger logger = LoggerFactory.getLogger(HistoryListener.class);
    private final TimeProvider timeProvider;
    private final Map<LocalDateTime, Message> history = new TreeMap<>(Collections.reverseOrder());

    public HistoryListener(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public void onUpdated(Message msg) {
        history.put(timeProvider.getTime(), deepCopy(msg));
        printHistory();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return history.values().stream().filter(m -> m.getId() == id).findFirst();
    }

    private static Message deepCopy(Message msg) {
        if (msg.getField13() == null) {
            return msg;
        } else {
            ObjectForMessage newOfm = new ObjectForMessage();
            newOfm.setData(List.copyOf(msg.getField13().getData()));
            return msg.toBuilder().field13(newOfm).build();
        }
    }

    private void printHistory() {
        for (Map.Entry<LocalDateTime, Message> entry : history.entrySet()) {
            logger.info("History entry {}: {}", entry.getKey(), entry.getValue());
        }
    }
}
