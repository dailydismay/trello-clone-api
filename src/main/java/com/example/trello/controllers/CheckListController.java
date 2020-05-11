package com.example.trello.controllers;

import com.example.trello.dtos.CreateCheckDto;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checks")
public class CheckListController {

    private CheckService checkService;

    @Autowired
    public CheckListController(CheckService checkService) {
        this.checkService = checkService;
    }

    @GetMapping
    public ResponseEntity list(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long cardId
    ) {
        return ResponseEntity.ok(checkService.list(cardId, jwtUser.getId()));
    }

    @PostMapping
    public ResponseEntity create(
            @RequestParam Long cardId,
            @RequestBody CreateCheckDto createCheckDto,
            @AuthenticationPrincipal JwtUser jwtUser
            ) {
        return ResponseEntity.ok(checkService.create(createCheckDto, cardId, jwtUser.getId()));
    }

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
