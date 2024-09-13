package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings({"java:S3011"})
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
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntity(rs);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
                    List<T> result = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            result.add(createEntity(rs));
                        }
                        return result;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new DataTemplateException(new RuntimeException("Error getting data")));
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> params = getParams(client);
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> params = getParams(client);
        params.add(getIdParam(client));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private List<Object> getParams(T client) {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        List<Object> params = new ArrayList<>();
        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
        return params;
    }

    private Object getIdParam(T client) {
        Field id = entityClassMetaData.getIdField();
        id.setAccessible(true);
        try {
            return id.get(client);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private T createEntity(ResultSet rs) {
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        Object[] params = new Object[constructor.getParameterCount()];
        try {
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                params[i] = rs.getObject(i + 1);
            }
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
