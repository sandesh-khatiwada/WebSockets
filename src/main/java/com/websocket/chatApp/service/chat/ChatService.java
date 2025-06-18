package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

        List<MessageResponse> getChatHistory(LocalDateTime createdAt, int limit);
}
