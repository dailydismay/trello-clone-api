package com.example.trello.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateCardDto {

    @NotNull
    private String title;

    private boolean archived;

    private Date deadline;
}
