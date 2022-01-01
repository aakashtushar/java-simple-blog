package org.aakashtushar.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractRepository<Entity> {
    protected final Connection connection;


    public AbstractRepository(Connection connection) {
        this.connection = connection;
    }

    protected Entity toEntity(ResultSet resultSet) throws SQLException {
        if (resultSet == null || !resultSet.next()) {
            return null;
        }

        return this.createEntityByResultSet(resultSet);
    }

    protected List<Entity> toEntityList(ResultSet resultSet) throws SQLException {
        List<Entity> entities = new ArrayList<Entity>();

        while (resultSet.next()) {
            entities.add(this.createEntityByResultSet(resultSet));
        }

        return entities;
    }

    protected abstract Entity createEntityByResultSet(ResultSet resultSet) throws SQLException;
}
