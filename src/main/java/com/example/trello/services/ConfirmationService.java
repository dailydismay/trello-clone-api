package com.example.trello.services;

import com.example.trello.models.UserEntity;

public interface ConfirmationService {
    void create(UserEntity user);
    void confirm(String link);
    boolean confirmed(UserEntity user);
}
