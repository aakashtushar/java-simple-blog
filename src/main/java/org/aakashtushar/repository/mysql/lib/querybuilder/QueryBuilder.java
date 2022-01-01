package org.aakashtushar.repository.mysql.lib.querybuilder;

import org.aakashtushar.repository.mysql.lib.criteria.Operator;
import org.aakashtushar.repository.mysql.lib.exception.LogicException;
import org.aakashtushar.repository.mysql.lib.param.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class QueryBuilder {
    private final Connection connection;
    private String select = "";
    private String from;
    private final String alias;
    private String join = "";
    private String where = "";
    private String orderBy = "";
    private Boolean insert = false;
    private Boolean update = false;
    private int limit;
    private int offset;
    private Map<Integer, ParameterValue> parameters = new HashMap<>();
    private Map<String, ParameterValue> columns = new HashMap<>();

    public QueryBuilder(Connection connection, String from, String alias) {
        this.connection = connection;
        this.from = from;
        this.alias = alias;
    }

    public QueryBuilder(Connection connection, String from) {
        this(connection, from, "t");
    }

    public QueryBuilder select(String columns) {
        this.select = columns + " ";

        return this;
    }

    public QueryBuilder addSelect(String columns) {
        this.select += columns + " ";

        return this;
    }

    public QueryBuilder from(String tableName) {
        this.from = tableName;

        return this;
    }

    private QueryBuilder doJoin(String type, String joinTable, String alias, String on) {
        this.join += (type + " JOIN " + joinTable + " AS " + alias + " ON " + on).trim();

        return this;
    }

    public QueryBuilder join(String joinTable, String alias, String on) {
        return this.doJoin("", joinTable, alias, on);
    }

    public QueryBuilder leftJoin(String joinTable, String alias, String on) {
        return this.doJoin("LEFT", joinTable, alias, on);
    }

    public QueryBuilder innerJoin(String joinTable, String alias, String on) {
        return this.doJoin("INNER", joinTable, alias, on);
    }

    public QueryBuilder where(String column, Operator operator, ParameterValue value) {
        this.where = makeWhere(column, operator, value);

        return this;
    }

    private String makeWhere(String column, Operator operator, ParameterValue value) {
        String where = column + " " + operator.getCode();
        if (operator.equals(Operator.EQUALS)
                || operator.equals(Operator.NOT_EQUALS)
                || operator.equals(Operator.GREATER_THAN)
                || operator.equals(Operator.GREATER_THAN_EQUALS)
                || operator.equals(Operator.LESS_THAN)
                || operator.equals(Operator.LESS_THAN_EQUALS)
        ) {
            where += " ?";
            parameters.put(parameters.size()+1, value);

            return where;
        } else if (operator.equals(Operator.IN)
                || operator.equals(Operator.BETWEEN)
        ) {
            where += " (";
            List list = (List) value.getValue();

            for (Object v : list) {
                where += " ?";

                ParameterValue newV;
                if (v instanceof Integer) {
                    newV = new IntParameterValue((Integer) v);
                } else if (v instanceof LocalDateTime) {
                    newV = new DateTimeParameterValue((LocalDateTime) v);
                } else {
                    newV = new StringParameterValue((String) v);
                }

                parameters.put(parameters.size()+1, newV);
            }

            return where + ")";
        } else if (operator.equals(Operator.IS_NULL)
                || operator.equals(Operator.IS_NOT_NULL)) {
            return where;
        }

        return null;
    }

    public QueryBuilder andWhere(String column, Operator operator, ParameterValue value) {
        this.where += " AND " + makeWhere(column, operator, value);

        return this;
    }

    public QueryBuilder orWhere(String column, Operator operator, ParameterValue value) {
        this.where += " OR " + makeWhere(column, operator, value);

        return this;
    }

    public QueryBuilder orderBy(String orderBy) {
        this.orderBy = orderBy;

        return this;
    }

    public QueryBuilder andOrderBy(String orderBy) {
        this.orderBy += " AND " + orderBy;

        return this;
    }

    public QueryBuilder orOrderBy(String orderBy) {
        this.orderBy += " OR " + orderBy;

        return this;
    }

    public QueryBuilder limit(int limit) {
        this.limit = limit;

        return this;
    }

    public QueryBuilder offset(int offset) {
        this.offset = offset;

        return this;
    }

    public QueryBuilder insert() {
        this.insert = true;

        return this;
    }

    public QueryBuilder update() {
        this.insert = true;

        return this;
    }

    public QueryBuilder setColumn(String column, ParameterValue value) {
        this.columns.put(column, value);

        return this;
    }

    public QueryBuilder setColumns(Map<String, ParameterValue> columns) {
        this.columns = columns;

        return this;
    }

    public QueryBuilder setParameters(Map<Integer, ParameterValue> parameters) {
        this.parameters = parameters;

        return this;
    }

    public QueryBuilder setParameter(int index, ParameterValue value) {
        this.parameters.put(index, value);

        return this;
    }

    public Query getQuery() throws LogicException {
        var queryString = this.createQueryString();
        var queryParams = new HashMap<Integer, ParameterValue>();
        var query = new Query(connection, queryString, queryParams);

        this.addQueryParameters(query);

        return query;
    }

    private void addQueryParameters(Query query) throws LogicException {
        var matcher = Pattern
                .compile("\\:\\w+")
                .matcher(query.getStmt());
        var stringBuilder = new StringBuilder();
        var i = 1;
        while (matcher.find()) {
            if (!parameters.containsKey(matcher.group())) {
                throw new LogicException(String.format("Parameter value for key %s missing.", matcher.group()));
            }

            var parameterValue = parameters.get(matcher.group());
            if (parameterValue.getType().equals(ParameterValueType.INT_LIST)
                    || parameterValue.getType().equals(ParameterValueType.STRING_LIST)
                    || parameterValue.getType().equals(ParameterValueType.DATETIME_LIST)) {
                String placeholders = "?";
                List<?> value = (List<?>) parameterValue.getValue();
                if (value.size() > 1) {
                    placeholders += ", ?".repeat(value.size() - 1);
                }
                matcher.appendReplacement(stringBuilder, placeholders);
                for (Object v : value) {
                    ParameterValue newV;
                    if (v instanceof Integer) {
                        newV = new IntParameterValue((Integer) v);
                    } else if (v instanceof LocalDateTime) {
                        newV = new DateTimeParameterValue((LocalDateTime) v);
                    } else {
                        newV = new StringParameterValue((String) v);
                    }
                    query.getParameters().put(i, newV);
                    i++;
                }
            } else {
                matcher.appendReplacement(stringBuilder, "?");
                query.getParameters().put(i, parameterValue);
                i++;
            }
        }
        System.out.println("query = " + query.getStmt());
        System.out.println("queryString = " + stringBuilder);
        query.setStmt(stringBuilder.toString());
    }

    private String createQueryString() {
        String queryString;
        if (insert) {
            queryString = getInsertQueryString();
        } else {
            queryString = getSelectQueryString();
        }

        return queryString +
                (!this.where.isBlank() ? "WHERE " + this.where.trim() + " " : "") +
                (!this.orderBy.isBlank() ? "ORDER BY " + this.orderBy.trim() + " " : "") +
                (this.limit > 0 ? "LIMIT " + this.limit + " " : "") +
                (this.offset > 0 ? "OFFSET " + this.offset + " " : "")
                ;
    }

    private String getSelectQueryString() {
        return (!this.select.isBlank() ? "SELECT " + this.select.trim() + " " : "SELECT * ") +
                this.from.trim() + " AS " + this.alias.trim() + " " +
                (!this.join.isBlank() ? (this.join.trim() + " ").trim() : "");
    }

    private String getInsertQueryString() {
        StringBuilder columnsString = new StringBuilder("(");
        StringBuilder valuesString = new StringBuilder("(");
        for (Map.Entry<String, ParameterValue> entry : columns.entrySet()) {
            columnsString.append(columnsString.toString().equals("(") ? "" : ", ").append(entry.getKey());
            valuesString.append(valuesString.toString().equals("(") ? "" : ", ").append("?");
            this.setParameter(parameters.size()+1, entry.getValue());
        }
        columnsString.append(")");
        valuesString.append(")");

        return "INSERT INTO " + this.from.trim() + " AS " + this.alias.trim() + " " + columnsString + " VALUES " + valuesString;
    }
}
