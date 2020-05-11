package com.example.trello.controllers;

import com.example.trello.dtos.LoginDto;
import com.example.trello.dtos.RegisterDto;
import com.example.trello.models.UserEntity;
import com.example.trello.security.jwt.JwtTokenProvider;
import com.example.trello.security.jwt.JwtUser;
import com.example.trello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        try {
            String username = loginDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));
            Optional<UserEntity> userGot = userService.findByUsername(username);

            if (!userGot.isPresent()) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            UserEntity user = userGot.get();

            String token = jwtTokenProvider.createToken(user.getId(), username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto) {
        System.out.println(registerDto.getEmail());
        UserEntity user = userService.register(registerDto);

        HashMap<String, Object> root = new HashMap<>();
        String token = jwtTokenProvider.createToken(user.getId(), user.getUsername(), user.getRoles());
        root.put("user", user);
        root.put("token", token);

        return ResponseEntity.ok(root);
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal JwtUser userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("user", userService.findByUsername(userDetails.getUsername()));
        model.put("authorities", userDetails.getAuthorities());
        return ResponseEntity.ok(model);
    }

}
