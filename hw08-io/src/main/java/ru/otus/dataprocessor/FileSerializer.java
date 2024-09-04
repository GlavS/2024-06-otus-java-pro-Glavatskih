package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final ObjectMapper objectMapper;
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        this.objectMapper = JsonMapper.builder()
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                .build();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        File file = new File(fileName);
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
