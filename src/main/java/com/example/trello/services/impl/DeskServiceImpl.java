package com.example.trello.services.impl;

import com.example.trello.dtos.CreateDeskDto;
import com.example.trello.exception.NotFoundException;
import com.example.trello.models.DeskEntity;
import com.example.trello.models.UserEntity;
import com.example.trello.repos.DeskRepo;
import com.example.trello.services.DeskService;
import com.example.trello.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class DeskServiceImpl implements DeskService {

    private UserService userService;
    private DeskRepo deskRepo;

    @Autowired
    public DeskServiceImpl(UserService userService, DeskRepo deskRepo) {
        this.userService = userService;
        this.deskRepo = deskRepo;
    }

    public DeskEntity create(CreateDeskDto createDeskDto, Long userId) {
        UserEntity user = userService.findById(userId);
        Set<UserEntity> users = new HashSet<>();
        users.add(user);
        DeskEntity desk  = DeskEntity.builder()
                .users(users)
                .name(createDeskDto.getName())
                .build();

        deskRepo.save(desk);

        return desk;
    }

    public DeskEntity findByIdAndUser(Long deskId, Long userId) {
        Optional<DeskEntity> optional = deskRepo.findById(deskId);

        if (!optional.isPresent()) {
            throw new NotFoundException("Post not found");
        }

        DeskEntity desk = optional.get();

        if (desk.getUsers().stream().noneMatch(x -> x.getId().equals(userId))) {
            throw new NotFoundException("Post not found");
        }

        return desk;
    }

    public Page<DeskEntity> findForUser(Pageable pageable, Long userId) {
        return deskRepo.findForUser(pageable, userId);
    }
    public DeskEntity addUser(Long deskId, Long userId, String email) {
        DeskEntity desk = findByIdAndUser(deskId, userId);
        UserEntity user = userService.findByEmail(email);
        if (desk.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            desk.getUsers().add(user);
        };

        return deskRepo.save(desk);
    }
}
