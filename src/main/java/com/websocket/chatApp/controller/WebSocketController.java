package com.websocket.chatApp.controller;

import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.dto.PrivateMessageRequest;
import com.websocket.chatApp.dto.PrivateMessageResponse;
import com.websocket.chatApp.service.websockets.WebSocketService;
import com.websocket.chatApp.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private final WebSocketService webSocketService;
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtUtil jwtUtil;


    @MessageMapping("/chat")
    public void sendMessage(MessageRequest messageDTO) {
        MessageResponse response = webSocketService.sendMessage(messageDTO);
        logger.info("Sending group message");
        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    @MessageMapping("/private-chat")
    public void sendPrivateMessage(PrivateMessageRequest messageRequest) {

        logger.info("Received private message request: {}", messageRequest);
        if (messageRequest.getReceiverUsername() == null || messageRequest.getReceiverUsername().isEmpty()) {
            logger.error("Receiver username is null or empty");
            throw new IllegalArgumentException("Receiver username cannot be null or empty");
        }
        PrivateMessageResponse response = webSocketService.sendPrivateMessage(messageRequest);


        String receiverId = messageRequest.getReceiverUsername();

        System.out.println("Receiver: "+ receiverId);
        if (receiverId == null || receiverId.isEmpty()) {
            logger.error("Invalid receiver ID for username: {}", messageRequest.getReceiverUsername());
            throw new IllegalArgumentException("Invalid receiver ID");
        }

        logger.info("Sending private message to user {} at /user/{}/queue/messages", receiverId, receiverId);

        logger.info("Message sent to : "+ receiverId);
        messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages", response);
    }
}
