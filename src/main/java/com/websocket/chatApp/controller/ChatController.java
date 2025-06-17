package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    public final ChatService chatService;

    @GetMapping("/history")
    public List<MessageResponse> getChatHistory(){
        return chatService.getChatHistory();
    }
}
