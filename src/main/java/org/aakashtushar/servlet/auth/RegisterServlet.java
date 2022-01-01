package org.aakashtushar.servlet.auth;

import org.aakashtushar.bootstrap.ServiceProvider;
import org.aakashtushar.request.RegisterRequest;
import org.aakashtushar.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/register"
)
public class RegisterServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var request = read(req, RegisterRequest.class);

        success(resp, ServiceProvider.getAuthenticationService().handleRegister(request));
    }
}
