package com.example.trello.services.impl;

import com.example.trello.dtos.CreateCheckDto;
import com.example.trello.exception.NotFoundException;
import com.example.trello.models.CardEntity;
import com.example.trello.models.CheckEntity;
import com.example.trello.repos.CheckRepo;
import com.example.trello.services.CardService;
import com.example.trello.services.CheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CheckServiceImpl implements CheckService {

    private CheckRepo checkRepo;
    private CardService cardService;

    public CheckServiceImpl(
            CheckRepo checkRepo,
            CardService cardService
    ) {
        this.checkRepo = checkRepo;
        this.cardService = cardService;
    }

    public CheckEntity create(CreateCheckDto createCheckDto, Long cardId, Long userId) {
        CardEntity card = cardService.findForUser(cardId, userId);

        log.info("Check for card with id " + card.getId() + " was created");
        return checkRepo.save(
                CheckEntity.builder()
                        .title(createCheckDto.getTitle())
                        .done(false)
                        .card(card)
                        .build()
        );
    }


    public CheckEntity makeDone(Long checkId, Long userId) {
        CheckEntity checkEntity = checkRepo.findForUser(checkId, userId).orElseThrow(() -> new NotFoundException("Check not found"));
        checkEntity.setDone(!checkEntity.isDone());
        return checkRepo.save(checkEntity);
    }

    public List<CheckEntity> list(Long cardId, Long userId) {
        CardEntity card = cardService.findForUser(cardId, userId);
        return checkRepo.findByCardOrderByCreatedDesc(card);
    }
}
