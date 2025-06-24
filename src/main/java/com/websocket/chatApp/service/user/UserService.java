package com.websocket.chatApp.service.user;

import com.websocket.chatApp.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();
}
