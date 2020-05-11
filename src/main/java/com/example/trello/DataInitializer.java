package com.example.trello;

import com.example.trello.models.RoleEntity;
import com.example.trello.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) {
        if (roleRepo.count() == 0) {
            roleRepo.save(
                    RoleEntity
                            .builder()
                            .name("ROLE_USER")
                            .build()
            );

            roleRepo.save(
                    RoleEntity
                            .builder()
                            .name("ROLE_ADMIN")
                            .build()
            );
        }
    }
}

