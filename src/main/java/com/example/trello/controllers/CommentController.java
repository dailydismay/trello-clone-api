package com.example.trello.controllers;

import com.example.trello.dtos.CreateCommentDto;
import com.example.trello.models.CommentEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentEntity> create(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long cardId,
            @RequestBody CreateCommentDto createCommentDto
            ) {
        return ResponseEntity.ok(commentService.create(createCommentDto, cardId, jwtUser.getId()));
    }

    @GetMapping
    public ResponseEntity<Page<CommentEntity>> list(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long cardId,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(commentService.findForCard(pageable, cardId, jwtUser.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal JwtUser jwtUser,
                                         @PathVariable Long id) {
        commentService.delete(id, jwtUser.getId());
        return ResponseEntity.status(204).build();
    }
}
