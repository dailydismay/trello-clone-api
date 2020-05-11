package com.example.trello.services;

import com.example.trello.dtos.CreateCardDto;
import com.example.trello.models.CardEntity;

public interface CardService {
    CardEntity create(Long listId, CreateCardDto createCardDto, Long userId);
    CardEntity archive(Long cardId, Long userId);
    CardEntity move(Long cardId, Long listId, Long userId);
    void deleteOne(Long cardId, Long userId);
    CardEntity findForUser(Long cardId, Long userId);
}
