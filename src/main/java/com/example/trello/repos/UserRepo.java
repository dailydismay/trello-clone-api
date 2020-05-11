package com.example.trello.repos;

import com.example.trello.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername (String name);
    Optional<UserEntity> findByEmailOrUsername(String email, String name);
    Optional<UserEntity> findByEmail(String email);
}
