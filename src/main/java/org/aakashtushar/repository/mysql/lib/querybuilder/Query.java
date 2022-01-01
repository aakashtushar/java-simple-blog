package org.aakashtushar.repository.mysql.lib.querybuilder;

import org.aakashtushar.repository.mysql.lib.param.DateTimeParameterValue;
import org.aakashtushar.repository.mysql.lib.param.IntParameterValue;
import org.aakashtushar.repository.mysql.lib.param.ParameterValue;
import org.aakashtushar.repository.mysql.lib.param.StringParameterValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class Query {
    private final Connection connection;
    private String stmt;
    private Map<Integer, ParameterValue> parameters;

    public Query(Connection connection, String stmt, Map<Integer, ParameterValue> parameters) {
        this.connection = connection;
        this.stmt = stmt;
        this.parameters = parameters;
    }

    public String getStmt() {
        return stmt;
    }

    public void setStmt(String stmt) {
        this.stmt = stmt;
    }

    public Map<Integer, ParameterValue> getParameters() {
        return parameters;
    }

    public boolean execute() throws SQLException {
        System.out.println(stmt);
        var statement = connection.prepareStatement(stmt);
        parameters.forEach((key, value) -> {
            try {
                if (value instanceof IntParameterValue) {
                statement.setInt(key, ((IntParameterValue) value).getValue());
                } else if (value instanceof StringParameterValue) {
                    statement.setString(key, ((StringParameterValue) value).getValue());
                } else if (value instanceof DateTimeParameterValue) {
                    statement.setTimestamp(key, Timestamp.valueOf(((DateTimeParameterValue) value).getValue()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return statement.execute();
    }

    public ResultSet getResultSet() throws SQLException {
        var statement = connection.prepareStatement(stmt);

        return statement.getResultSet();
    }
}
