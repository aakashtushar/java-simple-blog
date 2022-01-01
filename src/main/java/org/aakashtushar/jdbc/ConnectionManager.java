package org.aakashtushar.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements ConnectionManagerInterface {
    private final String url;
    private final String user;
    private final String pass;

    public ConnectionManager(String driver, String host, int port, String dbname, String user, String pass) {
        this.url = "jdbc:"+driver+"://"+ host +":"+ port +"/"+ dbname;
        this.user = user;
        this.pass = pass;
    }

    public ConnectionManager(String driver, String host, String dbname, String user, String pass) {
        this(driver, host, 3306, dbname, user, pass);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
