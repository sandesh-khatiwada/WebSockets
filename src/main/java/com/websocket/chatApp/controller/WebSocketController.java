package com.websocket.chatApp.controller;

import com.websocket.chatApp.mapper.MessageMapper;
import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.service.websockets.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    private final MessageMapper messageMapper;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(MessageRequest messageDTO) {

        return webSocketService.sendMessage(messageDTO);

    }
}