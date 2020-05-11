package com.example.trello.services;

import com.example.trello.dtos.RegisterDto;
import com.example.trello.models.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String name);
    UserEntity register(RegisterDto registerDto);
    UserEntity findById(Long id);
    UserEntity findByEmail(String email);
}
