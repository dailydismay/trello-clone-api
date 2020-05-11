package com.example.trello.controllers;

import com.example.trello.dtos.CreateCardDto;
import com.example.trello.models.CardEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity create(
            @RequestBody CreateCardDto createCardDto,
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long listId
            ) {

        return ResponseEntity.status(201).body(
                cardService.create(listId, createCardDto, jwtUser.getId())
        );
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity archive(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        CardEntity card = cardService.archive(id, jwtUser.getId());
        return ResponseEntity.ok(card);
    }

    @PutMapping("/{id}/move")
    public ResponseEntity move(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long listId
    ) {
        return ResponseEntity.ok(cardService.move(id, listId, jwtUser.getId()));
    }
}
