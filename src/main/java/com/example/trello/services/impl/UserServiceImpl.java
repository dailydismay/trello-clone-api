package com.example.trello.services.impl;

import com.example.trello.dtos.RegisterDto;
import com.example.trello.exception.NotFoundException;
import com.example.trello.exception.UnprocessableEntityException;
import com.example.trello.models.RoleEntity;
import com.example.trello.models.UserEntity;
import com.example.trello.repos.RoleRepo;
import com.example.trello.repos.UserRepo;
import com.example.trello.services.ConfirmationService;
import com.example.trello.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    private final RoleRepo roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ConfirmationService confirmationService;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, RoleRepo roleRepository, UserRepo userRepo, ConfirmationService confirmationService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepo = userRepo;
        this.confirmationService = confirmationService;
    }

    public Optional<UserEntity> findByUsername(String name) {
        return userRepo.findByUsername(name);
    }

    @Override
    @Transactional
    public UserEntity register(RegisterDto registerDto) {
        Optional<UserEntity> found = userRepo.findByEmailOrUsername(registerDto.getEmail(), registerDto.getUsername());

        if (found.isPresent()) {
            throw new UnprocessableEntityException("Such user already exists");
        }

        RoleEntity roleUser = roleRepository.findByName("ROLE_USER");
        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        UserEntity user = UserEntity
                .builder()
                .roles(userRoles)
                .email(registerDto.getEmail())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .username(registerDto.getUsername())
                .build();

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        UserEntity registeredUser = userRepo.save(user);
        confirmationService.create(user);
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    public UserEntity findById(Long id) {
        return this.userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserEntity findByEmail(String email) {
        return this.userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public boolean canLogin(UserEntity userEntity) {
        return confirmationService.confirmed(userEntity);
    }

}
