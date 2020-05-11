package com.example.trello.services;

import com.example.trello.dtos.CreateCommentDto;
import com.example.trello.models.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentEntity create(CreateCommentDto createCommentDto, Long cardId, Long userId);
    Page<CommentEntity> findForCard(Pageable pageable, Long cardId, Long userId);
    void delete(Long commentId, Long userId);
}
