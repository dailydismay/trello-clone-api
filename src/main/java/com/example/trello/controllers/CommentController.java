package com.example.trello.controllers;

import com.example.trello.dtos.CreateCommentDto;
import com.example.trello.dtos.responses.CommentsResponse;
import com.example.trello.models.CheckEntity;
import com.example.trello.models.CommentEntity;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.CommentService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiResponse(code = 201, message = "comment create success", response = CommentEntity.class)
    @PostMapping
    public ResponseEntity<CommentEntity> create(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long cardId,
            @Valid @RequestBody CreateCommentDto createCommentDto
            ) {
        return ResponseEntity.ok(commentService.create(createCommentDto, cardId, jwtUser.getId()));
    }

    @ApiResponse(code = 200, message = "comment create success", response = CommentsResponse.class)
    @GetMapping
    public ResponseEntity<Page<CommentEntity>> list(
            @AuthenticationPrincipal JwtUser jwtUser,
            @RequestParam Long cardId,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(commentService.findForCard(pageable, cardId, jwtUser.getId()));
    }

    @ApiResponse(code = 204, message = "comment deleted success")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal JwtUser jwtUser,
                                         @PathVariable Long id) {
        commentService.delete(id, jwtUser.getId());
        return ResponseEntity.status(204).build();
    }
}
