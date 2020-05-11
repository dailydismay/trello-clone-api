package com.example.trello.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterDto {
    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
}
