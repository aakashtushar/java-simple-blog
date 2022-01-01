package org.aakashtushar.repository.mysql;

import org.aakashtushar.entity.User;
import org.aakashtushar.repository.UserRepositoryInterface;
import org.aakashtushar.repository.mysql.lib.criteria.Criteria;
import org.aakashtushar.repository.mysql.lib.exception.LogicException;
import org.aakashtushar.repository.mysql.lib.criteria.Field;
import org.aakashtushar.repository.mysql.lib.param.ParameterValue;
import org.aakashtushar.repository.mysql.lib.param.StringParameterValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserRepository extends AbstractMysqlRepository<User> implements UserRepositoryInterface {
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public UserRepository(Connection connection) {
        super(connection);
    }

    @Override
    public User findUserByUsername(String username) {
        Criteria criteria = new Criteria();
        criteria.add(new Field(USERNAME, new StringParameterValue(username)));

        try {
            return this.findOneBy(criteria);
        } catch (LogicException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addNew(String username, String password) {
        var columns = new HashMap<String, ParameterValue>();
        columns.put(USERNAME, new StringParameterValue(username));
        columns.put(PASSWORD, new StringParameterValue(password));

        try {
            return this.create(columns);
        } catch (LogicException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected User createEntityByResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());
        user.setUpdatedAt(resultSet.getTimestamp(UPDATED_AT).toLocalDateTime());

        return user;
    }
}
