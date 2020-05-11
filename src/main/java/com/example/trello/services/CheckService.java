package com.example.trello.services;

import com.example.trello.dtos.CreateCheckDto;
import com.example.trello.models.CheckEntity;

import java.util.List;

public interface CheckService {
    CheckEntity create(CreateCheckDto createCheckDto, Long cardId, Long userId);
    CheckEntity makeDone(Long checkId, Long userId);
    List<CheckEntity> list(Long cardId, Long userId);
//    List<CheckEntity> list(Long cardId, Long userId);
}
