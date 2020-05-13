package com.example.trello.dtos.responses;

import com.example.trello.models.DeskEntity;
import org.springframework.data.domain.Page;

public interface DesksResponse extends Page<DeskEntity> {
}
