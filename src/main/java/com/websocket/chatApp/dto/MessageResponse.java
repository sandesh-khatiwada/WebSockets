package com.websocket.chatApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long message_id;
    private String content;
    private Long user_id; // Matches JSON field
    private String user_name;
    private LocalDateTime created_at;


}