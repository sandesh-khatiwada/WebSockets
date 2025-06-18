package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.service.chat.ChatService;
import com.websocket.chatApp.util.APIResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    public final ChatService chatService;

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
}
