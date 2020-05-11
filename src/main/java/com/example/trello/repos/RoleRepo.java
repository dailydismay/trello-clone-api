package com.example.trello.repos;

import com.example.trello.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
