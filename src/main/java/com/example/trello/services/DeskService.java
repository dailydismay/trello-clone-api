package com.example.trello.services;

import com.example.trello.dtos.CreateDeskDto;
import com.example.trello.models.DeskEntity;
import com.example.trello.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface DeskService {
    DeskEntity create(CreateDeskDto createDeskDto, Long userId);
    DeskEntity findByIdAndUser(Long deskId, Long userId);
    Page<DeskEntity> findForUser(Pageable pageable, Long userId);
    DeskEntity addUser(Long deskId, Long userId, String email);
}
