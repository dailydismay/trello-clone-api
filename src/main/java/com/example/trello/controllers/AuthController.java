package com.example.trello.controllers;

import com.example.trello.dtos.LoginDto;
import com.example.trello.dtos.RegisterDto;
import com.example.trello.dtos.responses.AuthResponse;
import com.example.trello.dtos.responses.MeResponse;
import com.example.trello.exception.ForbiddenException;
import com.example.trello.models.UserEntity;
import com.example.trello.security.jwt.JwtTokenProvider;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @ApiResponse(code = 200, message = "successful login", response = AuthResponse.class)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            String username = loginDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));
            Optional<UserEntity> userGot = userService.findByUsername(username);

            if (!userGot.isPresent()) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            UserEntity user = userGot.get();

            if (!userService.canLogin(user)) {
                throw new ForbiddenException("Email not confirmed");
            }

            String token = jwtTokenProvider.createToken(user.getId(), username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);
            return ResponseEntity.ok(new AuthResponse(user, token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @ApiResponse(code = 200, message = "successful registration", response = UserEntity.class)
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto) {
        System.out.println(registerDto.getEmail());
        UserEntity user = userService.register(registerDto);

        return ResponseEntity.ok(user);
    }

    @ApiResponse(code = 200, message = "user authenticated", response = MeResponse.class)
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal JwtUser userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("user", userService.findByUsername(userDetails.getUsername()));
        return ResponseEntity.ok(model);
    }

}
