package org.aakashtushar.servlet.auth;

import org.aakashtushar.bootstrap.ServiceProvider;
import org.aakashtushar.request.LoginRequest;
import org.aakashtushar.service.AuthenticationService;
import org.aakashtushar.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/login"
)
public class LoginServlet extends BaseServlet {
    private AuthenticationService authenticationService = ServiceProvider.getAuthenticationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginRequest loginRequest = read(req, LoginRequest.class);

        authenticationService.handleLogin(loginRequest);

        success(resp, loginRequest);
    }
}
