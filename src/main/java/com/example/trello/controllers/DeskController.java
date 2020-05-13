package com.example.trello.controllers;

import com.example.trello.dtos.CreateDeskDto;
import com.example.trello.dtos.responses.DesksResponse;
import com.example.trello.models.CommentEntity;
import com.example.trello.models.DeskEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.DeskService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/desks")
public class DeskController {

    private DeskService deskService;

    @Autowired
    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @ApiResponse(code = 201, message = "desk create success", response = DeskEntity.class)
    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody CreateDeskDto createDeskDto,
            @AuthenticationPrincipal JwtUser userDetails
            ) {

        Map <String, Object> root = new HashMap<>();
        DeskEntity desk = deskService.create(createDeskDto, userDetails.getId());

        root.put("desk", desk);

        return ResponseEntity.status(201).body(root);
    }

    @ApiResponse(code = 200, message = "desks list success", response = DesksResponse.class)
    @GetMapping
    public ResponseEntity list( @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
                                @AuthenticationPrincipal JwtUser userDetails
    ) {
        return ResponseEntity.ok(deskService.findForUser(pageable, userDetails.getId()));
    }

    @ApiResponse(code = 200, message = "desks findOne success", response = DeskEntity.class)
    @GetMapping("/{id}")
    public ResponseEntity findOne(@AuthenticationPrincipal JwtUser userDetails, @PathVariable Long id) {
        return ResponseEntity.ok(deskService.findByIdAndUser(id, userDetails.getId()));
    }

    @ApiResponse(code = 200, message = "desks user add success", response = DeskEntity.class)
    @PostMapping("/{id}/user")
    public ResponseEntity addUser(
            @RequestParam String email,
            @AuthenticationPrincipal JwtUser user,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                deskService.addUser(id, user.getId(), email)
        );
    }
}
