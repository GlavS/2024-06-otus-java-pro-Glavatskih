package ru.otus.listener.homework;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {
    private final List<HistoryEntry> history = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        HistoryEntry entry = new HistoryEntry(deepCopy(msg), LocalDateTime.now());
        history.add(entry);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return history.stream()
                .map(HistoryEntry::message)
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    private static Message deepCopy(Message msg) {
        ObjectForMessage newOfm = new ObjectForMessage();
        newOfm.setData(List.copyOf(msg.getField13().getData()));
        return msg.toBuilder().field13(newOfm).build();
    }
}
