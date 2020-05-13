package com.example.trello.controllers;

import com.example.trello.dtos.CreateCheckDto;
import com.example.trello.dtos.responses.CheckResponse;
import com.example.trello.models.CardEntity;
import com.example.trello.models.CheckEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.CheckService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/checks")
public class CheckListController {

    private CheckService checkService;

    @Autowired
    public CheckListController(CheckService checkService) {
        this.checkService = checkService;
    }

    @ApiResponse(code = 200, message = "checks list success", response = CheckResponse.class)
    @GetMapping
    public ResponseEntity list(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long cardId
    ) {
        return ResponseEntity.ok(checkService.list(cardId, jwtUser.getId()));
    }

    @ApiResponse(code = 201, message = "checks created success", response = CheckEntity.class)
    @PostMapping
    public ResponseEntity create(
            @RequestParam Long cardId,
            @Valid @RequestBody CreateCheckDto createCheckDto,
            @AuthenticationPrincipal JwtUser jwtUser
            ) {
        return ResponseEntity.status(201).body(checkService.create(createCheckDto, cardId, jwtUser.getId()));
    }

    @ApiResponse(code = 201, message = "checks done success", response = CheckEntity.class)
    @PutMapping("/{id}/done")
    public ResponseEntity makeDone(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        return ResponseEntity.status(202)
                .body(
                    checkService.makeDone(id, jwtUser.getId())
                );
    }
}
