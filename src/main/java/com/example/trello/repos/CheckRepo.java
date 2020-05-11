package com.example.trello.repos;

import com.example.trello.models.CardEntity;
import com.example.trello.models.CheckEntity;
import com.example.trello.models.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CheckRepo extends JpaRepository<CheckEntity, Long> {
    Page<CheckEntity> findByCard(Pageable pageable, CardEntity cardEntity);

    @Query("select distinct c from CheckEntity" +
            " c join c.card card" +
            " join card.list l" +
            " join l.desk d" +
            " join d.users u" +
            " where c.id = :checkId and u.id = :userId")
    Optional<CheckEntity> findForUser(Long checkId, Long userId);

    List<CheckEntity> findByCardOrderByCreatedDesc(CardEntity cardEntity);
}
