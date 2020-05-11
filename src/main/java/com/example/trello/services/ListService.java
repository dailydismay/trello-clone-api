package com.example.trello.services;

import com.example.trello.dtos.CreateListDto;
import com.example.trello.models.ListEntity;
import com.example.trello.models.UserEntity;
import com.example.trello.security.jwt.JwtUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ListService {
    ListEntity create(
            Long deskId,
            CreateListDto createListDto,
            JwtUser userDetails
    );

//    List<ListEntity> findForDesk(Long deskId, UserEntity userEntity);
    List<ListEntity> findForDesk(Long deskId, Long userId);
    ListEntity findForUser(Long listId, Long userId);
}
