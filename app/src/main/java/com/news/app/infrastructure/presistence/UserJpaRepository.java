package com.news.app.infrastructure.presistence;

import java.util.Optional;

public interface UserJpaRepository {
    Optional<UserJpaEntity> findById(String userId);
}
