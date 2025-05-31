package com.news.app.domain.services;

import org.springframework.stereotype.Service;

import com.news.app.domain.modal.User;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public User authenticate(String username, String password) {
        if ("admin".equals(username) && "password".equals(password)) {
            User user = new User();
            user.setUsername(username);
            return user;
        }
        throw new RuntimeException("Invalid credentials");
    }

}
