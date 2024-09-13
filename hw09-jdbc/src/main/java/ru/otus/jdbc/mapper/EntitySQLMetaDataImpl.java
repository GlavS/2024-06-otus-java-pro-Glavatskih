package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData;
    private final String tableName;
    private final String idColumnName;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        tableName = entityClassMetaData.getName();
        idColumnName = entityClassMetaData.getIdField().getName();
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + tableName + " where " + idColumnName + " = ?";
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        int paramCount = fieldsWithoutId.size();
        StringBuilder names = new StringBuilder(fieldsWithoutId.getFirst().getName());
        fieldsWithoutId.removeFirst();
        for (Field field : fieldsWithoutId) {
            names.append(", ").append(field.getName());
        }
        StringBuilder params = new StringBuilder("?");
        for (int i = 1; i < paramCount; i++) {
            params.append(", ").append("?");
        }
        // "insert into client (name) values ( ? );"
        return "insert into " + tableName + " (" + names + ") values (" + params + ")";
    }

    @Override
    public String getUpdateSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        StringBuilder names = new StringBuilder(fieldsWithoutId.getFirst().getName());
        names.append(" = ?");
        fieldsWithoutId.removeFirst();
        for (Field field : fieldsWithoutId) {
            names.append(", ").append(field.getName()).append(" = ?");
        }
        // update client set name = ? where id = ?;
        return "update " + tableName + " set " + names + "where " + idColumnName + " = ?";
    }
}
