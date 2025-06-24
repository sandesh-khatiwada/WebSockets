package com.websocket.chatApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateMessageResponse {

    private Long message_id;
    private String content;
    private Long sender_id;
    private Long receiver_id;// Matches JSON field
    private String sender_username;
    private String receiver_username;
    private LocalDateTime created_at;
}
