package com.example.trello.controllers;

import com.example.trello.dtos.CreateCardDto;
import com.example.trello.dtos.responses.AuthResponse;
import com.example.trello.models.CardEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.CardService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @ApiResponse(code = 200, message = "card created success", response = CardEntity.class)
    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody CreateCardDto createCardDto,
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long listId
            ) {

        return ResponseEntity.status(201).body(
                cardService.create(listId, createCardDto, jwtUser.getId())
        );
    }

    @ApiResponse(code = 200, message = "card archived success", response = CardEntity.class)
    @PutMapping("/{id}/archive")
    public ResponseEntity archive(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUser jwtUser
    ) {
        CardEntity card = cardService.archive(id, jwtUser.getId());
        return ResponseEntity.ok(card);
    }

    @ApiResponse(code = 200, message = "card moved success", response = CardEntity.class)
    @PutMapping("/{id}/move")
    public ResponseEntity move(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long listId
    ) {
        return ResponseEntity.ok(cardService.move(id, listId, jwtUser.getId()));
    }

    @ApiResponse(code = 204, message = "card removed success")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(
            @AuthenticationPrincipal JwtUser jwtUser,
            @PathVariable Long id
    ) {
        cardService.deleteOne(id, jwtUser.getId());
        return ResponseEntity.status(204).build();
    }
}
