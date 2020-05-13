package com.example.trello.controllers;

import com.example.trello.services.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/confirmation")
@RestController
public class ConfirmationController {
    private ConfirmationService confirmationService;

    @Autowired
    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @GetMapping
    public ResponseEntity confirm(
            @RequestParam String link
    ) {
        confirmationService.confirm(link);
        return ResponseEntity.status(200).build();
    }
}
