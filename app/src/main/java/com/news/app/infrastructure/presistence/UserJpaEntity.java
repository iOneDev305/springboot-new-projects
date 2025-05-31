package com.news.app.infrastructure.presistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserJpaEntity {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
}
