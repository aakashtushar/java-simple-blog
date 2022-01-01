package org.aakashtushar.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManagerInterface {
    Connection getConnection() throws SQLException;
}
