package org.aakashtushar.jdbc;

import org.aakashtushar.jdbc.configuration.ConfigLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionProvider {
    private Map<String, Connection> connections = new HashMap<>();

    public ConnectionProvider() {
        ConfigLoader.load();
    }

    public Connection getConnection(String connectionName) throws SQLException {
        if (!connections.containsKey(connectionName)) {
            var connectionConfig = ConfigLoader.getConnectionConfigs().get(connectionName);
            connections.put(connectionName, (new ConnectionManager(
                    connectionConfig.getDriver(),
                    connectionConfig.getHost(),
                    connectionConfig.getPort(),
                    connectionConfig.getDbName(),
                    connectionConfig.getUsername(),
                    connectionConfig.getPassword()
            )).getConnection());
        }

        return connections.get(connectionName);
    }

    public Connection getConnection() throws SQLException {
        return getConnection(ConfigLoader.getDefaultConnection());
    }
}
