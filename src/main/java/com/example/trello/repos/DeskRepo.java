package com.example.trello.repos;

import com.example.trello.models.DeskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DeskRepo extends JpaRepository<DeskEntity, Long> {
//    Optional<DeskEntity> findById(Long id, UserEntity userEntity);
//    Page<DeskEntity> findByUsersContaining(Pageable pageable, UserEntity userEntity);
//    Optional<DeskEntity> findByUsersContainingAndId(UserEntity userEntity, Long id);

    @Query("select distinct d from DeskEntity d  join d.users u where u.id = :userId")
    Page<DeskEntity> findForUser(Pageable pageable, Long userId);
}
