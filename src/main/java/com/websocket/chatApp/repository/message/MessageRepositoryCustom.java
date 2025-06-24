package com.websocket.chatApp.repository.message;

import com.websocket.chatApp.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> findTopNMessages(int limit);
    List<Message> findMessagesBefore(LocalDateTime createdAt, int limit);
}