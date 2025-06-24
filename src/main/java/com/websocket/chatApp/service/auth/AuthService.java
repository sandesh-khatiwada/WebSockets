package com.websocket.chatApp.service.auth;

import com.websocket.chatApp.dto.LoginRequest;
import com.websocket.chatApp.dto.LoginResponse;


public interface AuthService {

    LoginResponse login(LoginRequest loginRequestDTO);
}
