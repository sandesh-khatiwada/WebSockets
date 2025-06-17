package com.websocket.chatApp.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String content;
    private String token;


}