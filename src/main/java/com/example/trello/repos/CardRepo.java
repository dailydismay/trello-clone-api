package com.example.trello.repos;

import com.example.trello.models.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface CardRepo extends JpaRepository<CardEntity, Long> {
    @Query("select c from CardEntity c join c.list l join l.desk d join d.users u where c.id = :cardId and u.id = :userId")
    Optional<CardEntity> findForUser(Long cardId, Long userId);
}
