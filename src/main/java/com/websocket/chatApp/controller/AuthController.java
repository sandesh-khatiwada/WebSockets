package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.LoginRequest;
import com.websocket.chatApp.dto.LoginResponse;
import com.websocket.chatApp.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequestDTO){
        return authService.login(loginRequestDTO);
    }


}
