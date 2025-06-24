package com.websocket.chatApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long messageId;
    private String content;
    private Long userId; // Matches JSON field
    private String senderUsername;
    private LocalDateTime createdAt;


}