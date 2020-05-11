package com.example.trello.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCheckDto {
    @NotNull
    private String title;
}
