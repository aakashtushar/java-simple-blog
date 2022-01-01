package org.aakashtushar.jdbc.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {
    private static Map<String, ConnectionConfig> connectionConfigs = new HashMap<>();
    private static String defaultConnection;

    public static Map<String, ConnectionConfig> getConnectionConfigs() {
        return connectionConfigs;
    }

    public static String getDefaultConnection() {
        return defaultConnection;
    }

    public static void load() {
        var properties = new Properties();
        try {
            var inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config/database.properties");
            properties.load(inputStream);

            properties.forEach((key, value) -> {
                String stringKey = (String) key;
                if (stringKey.equals("default")) {
                    defaultConnection = (String) value;

                    return;
                }

                var keyParts = stringKey.split("\\.");
                var connectionName = keyParts[0];
                if (!connectionConfigs.containsKey(connectionName)) {
                    connectionConfigs.put(connectionName, new ConnectionConfig());
                }

                switch (keyParts[1]) {
                    case "driver":
                        connectionConfigs.get(connectionName).setDriver((String) value);
                        break;
                    case "host":
                        connectionConfigs.get(connectionName).setHost((String) value);
                        break;
                    case "port":
                        connectionConfigs.get(connectionName).setPort(Integer.parseInt((String) value));
                        break;
                    case "dbname":
                        connectionConfigs.get(connectionName).setDbName((String) value);
                        break;
                    case "username":
                        connectionConfigs.get(connectionName).setUsername((String) value);
                        break;
                    case "password":
                        connectionConfigs.get(connectionName).setPassword((String) value);
                        break;
                }
            });

        } catch (IOException e) {
            System.out.println("Could not load database.properties configuration.");
            System.out.println(e.getMessage());
        }
    }
}
