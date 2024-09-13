package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> entityClass;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        Class<?>[] parameterTypes = getAllArgsConstructor().getParameterTypes();
        try {
            return entityClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        Field[] declaredFields = entityClass.getDeclaredFields();
        Deque<Field> result = new ArrayDeque<>();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) {
                result.push(field);
            }
        }
        if (result.isEmpty()) {
            throw new EntityClassMetaDataException("No id field in entity class");
        } else if (result.size() > 1) {
            throw new EntityClassMetaDataException("Multiple id fields in entity class");
        }
        return result.pop();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(entityClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> result = new ArrayList<>();
        for (Field field : getAllFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                result.add(field);
            }
        }
        return result;
    }

    private Constructor<?> getAllArgsConstructor() {
        Constructor<?>[] entityClassConstructors = entityClass.getConstructors();
        Arrays.sort(entityClassConstructors, Comparator.comparingInt(Constructor::getParameterCount));
        Constructor<?> maxArgsConstructor =  entityClassConstructors[entityClassConstructors.length - 1];
        if (maxArgsConstructor.getParameterCount() < getAllFields().size()) {
            throw new RuntimeException("No all-args constructor in entity class");
        }
        return maxArgsConstructor;
    }
}
