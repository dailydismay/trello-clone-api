package com.example.trello.repos;

import com.example.trello.models.ListEntity;
import com.example.trello.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListRepo extends JpaRepository<ListEntity, Long> {
    @Query("select l from ListEntity l" +
            " join l.desk d join d.users u where d.id = :deskId and u.id = :userId order by l.created asc")
    List<ListEntity> findForDesk(Long deskId, Long userId);

    @Query("select distinct l from ListEntity l join l.desk d join d.users u where l.id = :id and u.id = :userId")
    Optional<ListEntity> findForUser(Long id, Long userId);
}
