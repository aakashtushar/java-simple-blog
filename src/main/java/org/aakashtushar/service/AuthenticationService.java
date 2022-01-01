package org.aakashtushar.service;

import org.aakashtushar.response.ErrorResponse;
import org.aakashtushar.entity.User;
import org.aakashtushar.repository.UserRepositoryInterface;
import org.aakashtushar.repository.mysql.lib.exception.LogicException;
import org.aakashtushar.request.LoginRequest;
import org.aakashtushar.request.RegisterRequest;
import org.aakashtushar.response.auth.UserCreatedResponse;

import java.sql.SQLException;

public class AuthenticationService {
    private UserRepositoryInterface userRepository;

    public AuthenticationService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public User handleLogin(LoginRequest loginRequest) {
        try {
            return userRepository.findUserByUsername("hahaha");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (LogicException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object handleRegister(RegisterRequest request) {

        try {
            if (userRepository.findUserByUsername(request.getUsername()) != null) {
                return (ErrorResponse) () -> "Username already exists. Please use a different username.";
            }

            boolean OK = userRepository.addNew(request.getUsername(), request.getPassword());

            return new UserCreatedResponse(OK);
        } catch (SQLException | LogicException e) {
            e.printStackTrace();
        }

        return null;
    }
}
