package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.dto.PrivateMessageResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

        List<MessageResponse> getChatHistory(LocalDateTime createdAt, int limit);
        List<PrivateMessageResponse> getPrivateChatHistory(String receiverUsername, String senderUsername, int limit, LocalDateTime createdAt);
}
