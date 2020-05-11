package com.example.trello.services.impl;

import com.example.trello.dtos.CreateCommentDto;
import com.example.trello.exception.NotFoundException;
import com.example.trello.models.CardEntity;
import com.example.trello.models.CommentEntity;
import com.example.trello.repos.CommentRepo;
import com.example.trello.services.CardService;
import com.example.trello.services.CommentService;
import com.example.trello.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepo commentRepo;
    private CardService cardService;
    private UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo, CardService cardService, UserService userService) {
        this.commentRepo = commentRepo;
        this.cardService = cardService;
        this.userService = userService;
    }

    public CommentEntity create(CreateCommentDto createCommentDto, Long cardId, Long userId) {
        CardEntity card = cardService.findForUser(cardId, userId);

        CommentEntity commentEntity = CommentEntity.builder()
                .card(card)
                .user(userService.findById(userId))
                .content(createCommentDto.getContent())
                .build();

        return commentRepo.save(commentEntity);
    }

    public Page<CommentEntity> findForCard(Pageable pageable, Long cardId, Long userId) {
        CardEntity card = cardService.findForUser(cardId, userId);
        return commentRepo.findByCard(pageable, card);
    }
    public void delete(Long commentId, Long userId) {
        CommentEntity comment = commentRepo.findForUser(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
        log.info("Comment with id " + comment.getId() + " was deleted");
        commentRepo.delete(comment);
    }
}
