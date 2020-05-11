package com.example.trello.controllers;

import com.example.trello.dtos.CreateListDto;
import com.example.trello.models.ListEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.ListService;
import com.example.trello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    private ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping
    public ResponseEntity create(
            @RequestBody CreateListDto createListDto,
            @AuthenticationPrincipal JwtUser userDetails,
            @RequestParam Long deskId
            ) {
        ListEntity list = listService.create(deskId, createListDto, userDetails);

        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity list(@AuthenticationPrincipal JwtUser jwtUser,
                               @RequestParam Long deskId) {
        return ResponseEntity.ok(listService.findForDesk(deskId, jwtUser.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @AuthenticationPrincipal JwtUser jwtUser,
            @PathVariable Long id
    ) {
        listService.deleteOne(id, jwtUser.getId());
        return ResponseEntity.status(204).build();
    }
}
