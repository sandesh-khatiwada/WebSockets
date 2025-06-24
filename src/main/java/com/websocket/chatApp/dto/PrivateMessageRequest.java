package com.websocket.chatApp.dto;

import lombok.Data;

@Data
public class PrivateMessageRequest {

    private String receiverUsername;
    private String content;
    private String token;
}
