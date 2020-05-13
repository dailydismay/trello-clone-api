package com.example.trello.dtos.responses;

import com.example.trello.models.UserEntity;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private UserEntity user;
    private String token;
}
