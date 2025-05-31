package com.news.app.domain.services;

import com.news.app.domain.modal.User;

public interface AuthService {
    User authenticate(String username, String password);
}
