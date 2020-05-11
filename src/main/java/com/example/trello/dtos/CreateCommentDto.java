package com.example.trello.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentDto {
    @NotNull
    private String content;
}
