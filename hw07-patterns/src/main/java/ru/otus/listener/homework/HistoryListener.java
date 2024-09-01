package ru.otus.listener.homework;

import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {
    private static final Logger logger = LoggerFactory.getLogger(HistoryListener.class);
    private final List<HistoryEntry> history = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        HistoryEntry entry = new HistoryEntry(deepCopy(msg), LocalDateTime.now());
        history.add(entry);
        printHistory();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return history.stream()
                .map(HistoryEntry::message)
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    private static Message deepCopy(Message msg) {
        if(msg.getField13() == null) {
            return msg;
        } else {
            ObjectForMessage newOfm = new ObjectForMessage();
            newOfm.setData(List.copyOf(msg.getField13().getData()));
            return msg.toBuilder().field13(newOfm).build();
        }
    }

    private void printHistory() {
        for (int i = 0; i < history.size(); i++) {
            logger.info("History entry {}: {}", i, history.get(i));
        }
    }
}
