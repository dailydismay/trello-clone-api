package com.example.trello.repos;

import com.example.trello.models.ConfirmationEntity;
import com.example.trello.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationRepo extends JpaRepository<ConfirmationEntity, Long> {
    Optional<ConfirmationEntity> findByUser(UserEntity user);
    Optional<ConfirmationEntity> findByLink(String link);
}
