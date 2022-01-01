package org.aakashtushar.repository;

import org.aakashtushar.entity.User;
import org.aakashtushar.repository.mysql.lib.exception.LogicException;

import java.sql.SQLException;

public interface UserRepositoryInterface extends BaseRepositoryInterface<User> {
    User findUserByUsername(String username) throws SQLException, LogicException;
    boolean addNew(String username, String password);
}
