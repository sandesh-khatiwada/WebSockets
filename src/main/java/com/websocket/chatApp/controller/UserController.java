package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.UserResponse;
import com.websocket.chatApp.service.user.UserService;
import com.websocket.chatApp.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<APIResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> users= userService.getAllUsers();


        APIResponse<List<UserResponse>> userResponse = new APIResponse<>(
                HttpStatus.OK,
                "Users retrieved succesfully",
                users
        );

        return new ResponseEntity<>(userResponse, HttpStatus.OK );
    }
}
