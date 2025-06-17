package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;

import java.util.List;

public interface ChatService {

        List<MessageResponse> getChatHistory();
}
