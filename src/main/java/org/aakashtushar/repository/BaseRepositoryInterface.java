package org.aakashtushar.repository;

import org.aakashtushar.repository.mysql.lib.criteria.Criteria;
import org.aakashtushar.repository.mysql.lib.exception.LogicException;

import java.sql.SQLException;
import java.util.List;

public interface BaseRepositoryInterface<Entity> {
    List<Entity> findBy(Criteria criteria) throws LogicException, SQLException;

    Entity findOneBy(Criteria criteria) throws LogicException, SQLException;

    List<Entity> getAll() throws SQLException, LogicException;
}
