package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

@SuppressWarnings({"java:S125"})
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData;
    private final String tableName;
    private final String idColumnName;

    private static final String SELECT_FROM = "select * from ";
    private static final String INSERT_INTO = "insert into ";
    private static final String UPDATE = "update ";
    private static final String SET = " set ";
    private static final String VALUES = " values ";
    private static final String WHERE = " where ";
    private static final String PARAM = "?";
    private static final String EQUALS_PARAM = " = ? ";
    private static final String COMMA = ", ";
    private static final String OPEN_BRACKET = " ( ";
    private static final String CLOSE_BRACKET = " ) ";

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        tableName = entityClassMetaData.getName();
        idColumnName = entityClassMetaData.getIdField().getName();
    }

    @Override
    public String getSelectAllSql() {
        return SELECT_FROM + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        return SELECT_FROM + tableName + WHERE + idColumnName + EQUALS_PARAM;
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        int paramCount = fieldsWithoutId.size();
        StringBuilder names = new StringBuilder(fieldsWithoutId.getFirst().getName());
        fieldsWithoutId.removeFirst();
        for (Field field : fieldsWithoutId) {
            names.append(COMMA).append(field.getName());
        }
        StringBuilder params = new StringBuilder(PARAM);
        for (int i = 1; i < paramCount; i++) {
            params.append(COMMA).append(PARAM);
        }
        // "insert into table_name (name1, name2) values ( ?, ? );"
        return INSERT_INTO
                + tableName
                + OPEN_BRACKET
                + names
                + CLOSE_BRACKET
                + VALUES
                + OPEN_BRACKET
                + params
                + CLOSE_BRACKET;
    }

    @Override
    public String getUpdateSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        StringBuilder names = new StringBuilder(fieldsWithoutId.getFirst().getName());
        names.append(EQUALS_PARAM);
        fieldsWithoutId.removeFirst();
        for (Field field : fieldsWithoutId) {
            names.append(COMMA).append(field.getName()).append(EQUALS_PARAM);
        }
        // update table_name set name1 = ?, name2 = ? where id = ?;
        return UPDATE + tableName + SET + names + WHERE + idColumnName + EQUALS_PARAM;
    }
}
