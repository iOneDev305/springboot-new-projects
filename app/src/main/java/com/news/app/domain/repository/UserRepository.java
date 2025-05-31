package com.news.app.domain.repository;

import java.util.Optional;
import com.news.app.domain.modal.User;

public interface UserRepository {
    Optional<User> getUserById(String userId);
    void saveUser(User user);
}
