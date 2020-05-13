package com.example.trello.dtos.responses;

import com.example.trello.models.CommentEntity;
import org.springframework.data.domain.Page;

public interface CommentsResponse extends Page<CommentEntity> {
}
