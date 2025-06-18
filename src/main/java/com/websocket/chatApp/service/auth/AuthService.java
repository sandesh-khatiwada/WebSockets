package com.websocket.chatApp.service.auth;

import com.websocket.chatApp.dto.LoginRequest;
import com.websocket.chatApp.dto.LoginResponse;
import com.websocket.chatApp.util.APIResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequestDTO);
}
