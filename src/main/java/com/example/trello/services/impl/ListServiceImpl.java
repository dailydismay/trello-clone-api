package com.example.trello.services.impl;

import com.example.trello.dtos.CreateListDto;
import com.example.trello.exception.NotFoundException;
import com.example.trello.models.DeskEntity;
import com.example.trello.models.ListEntity;
import com.example.trello.models.UserEntity;
import com.example.trello.repos.ListRepo;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.DeskService;
import com.example.trello.services.ListService;
import com.example.trello.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListServiceImpl implements ListService {

    private ListRepo listRepo;
    private DeskService deskService;


    public ListServiceImpl(
            ListRepo listRepo,
            DeskService deskService
    ) {
        this.listRepo = listRepo;
        this.deskService = deskService;
    }

    public ListEntity create(
            Long deskId,
            CreateListDto createListDto,
            JwtUser userDetails
    ) {
        DeskEntity deskEntity = deskService.findByIdAndUser(deskId, userDetails.getId());

        ListEntity list = ListEntity.builder()
                .desk(deskEntity)
                .title(createListDto.getTitle())
                .build();

        return listRepo.save(list);
    }

    public List<ListEntity> findForDesk(Long deskId, Long userId) {
        return listRepo.findForDesk(deskId, userId);
    }
    public ListEntity findForUser(Long listId, Long userId) {
        return listRepo.findForUser(listId, userId).orElseThrow(() -> new NotFoundException("List not found"));
    }

    public void deleteOne(Long listId, Long userId) {
        ListEntity list = findForUser(listId, userId);
        listRepo.delete(list);
    }
}
