package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.mapper.MessageMapper;
import com.websocket.chatApp.service.websockets.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(MessageRequest messageDTO) {
        MessageResponse response = webSocketService.sendMessage(messageDTO);
        messagingTemplate.convertAndSend("/topic/messages", response);
    }
}
