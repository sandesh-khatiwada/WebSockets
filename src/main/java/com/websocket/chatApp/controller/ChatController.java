package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.dto.PrivateMessageResponse;
import com.websocket.chatApp.service.chat.ChatService;
import com.websocket.chatApp.util.APIResponse;
import com.websocket.chatApp.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    public final ChatService chatService;
    private JwtUtil jwtUtil;


    @GetMapping("/history")
    public ResponseEntity<APIResponse<List<MessageResponse>>> getChatHistory(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime lastMessageCreatedAt,
            @RequestParam(defaultValue = "20") int limit
    ){

        List<MessageResponse> response = chatService.getChatHistory(lastMessageCreatedAt,limit);

        APIResponse chatHistoryResponse = new APIResponse<>(
                HttpStatus.OK,
                "Chat history retrieved succesfully",
                response
        );

        return new ResponseEntity<>(chatHistoryResponse, HttpStatus.OK);
    }

    @GetMapping("/private-history")

    public ResponseEntity<APIResponse<List<PrivateMessageResponse>>> getPrivateChatHistory(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(required = true) String receiverUsername,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime lastMessageCreatedAt
            ){


        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Authorization Header missing");

        }

        String token = authorizationHeader.substring(7);

        String senderUsername;

        senderUsername = jwtUtil.getUsernameFromToken(token);

        List<PrivateMessageResponse> response = chatService.getPrivateChatHistory(receiverUsername, senderUsername,limit, lastMessageCreatedAt);

        APIResponse chatHistoryResponse = new APIResponse<>(
                HttpStatus.OK,
                "Chat history retrieved succesfully",
                response
        );

        return new ResponseEntity<>(chatHistoryResponse, HttpStatus.OK);

    }
}
