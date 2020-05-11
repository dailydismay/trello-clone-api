package com.example.trello.repos;

import com.example.trello.models.CardEntity;
import com.example.trello.models.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByCard(Pageable pageable, CardEntity cardEntity);
    @Query("select c from CommentEntity c join c.user u where c.id = :commentId and u.id = :userId")
    Optional<CommentEntity> findForUser(Long commentId, Long userId);
}
