package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.LoginRequest;
import com.websocket.chatApp.dto.LoginResponse;
import com.websocket.chatApp.service.auth.AuthService;
import com.websocket.chatApp.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequestDTO){

        LoginResponse loginResponse = authService.login(loginRequestDTO);

        APIResponse<LoginResponse> response = new APIResponse<>(
                HttpStatus.OK,
                "Login succesfull",
                loginResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
