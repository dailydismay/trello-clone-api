package com.example.trello.repos;

import com.example.trello.models.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepo extends JpaRepository<CheckEntity, Long> {
}
