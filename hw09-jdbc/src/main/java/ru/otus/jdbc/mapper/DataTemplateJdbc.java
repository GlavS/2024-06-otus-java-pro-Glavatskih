package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            Object[] params = new Object[constructor.getParameterCount()];
            try {
                if (rs.next()) {
                    for (int i = 0; i < constructor.getParameterCount(); i++) {
                        params[i] = rs.getObject(i + 1);
                    }
                    return constructor.newInstance(params);
                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });

        // throw new UnsupportedOperationException();
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>();
        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
        // throw new UnsupportedOperationException();
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }
}
