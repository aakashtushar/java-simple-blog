package org.aakashtushar.bootstrap;

import org.aakashtushar.jdbc.ConnectionProvider;
import org.aakashtushar.repository.UserRepositoryInterface;
import org.aakashtushar.repository.mysql.UserRepository;
import org.aakashtushar.service.AuthenticationService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ServiceProvider {
    private static Map<Integer, Object> services = new HashMap<>();

    private ServiceProvider() {}

    private static Object singleton(Supplier<Object> function){
        if (!services.containsKey(function.hashCode())) {
            services.put(function.hashCode(), function.get());
        }

        return services.get(function.hashCode());
    }

    public static final ConnectionProvider getConnectionProvider() {
        return (ConnectionProvider) singleton(() -> new ConnectionProvider());
    }

    public static final UserRepositoryInterface getUserRepository() throws SQLException {
        return new UserRepository(
                ServiceProvider.getConnectionProvider().getConnection()
        );
    }

    public static final AuthenticationService getAuthenticationService() {
        try {
            return new AuthenticationService(
                    ServiceProvider.getUserRepository()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
