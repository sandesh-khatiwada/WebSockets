package com.websocket.chatApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateMessageResponse {

    private Long messageId;
    private String content;
    private Long senderId;
    private Long receiverId;// Matches JSON field
    private String senderUsername;
    private String receiverUsername;
    private LocalDateTime createdAt;
}
