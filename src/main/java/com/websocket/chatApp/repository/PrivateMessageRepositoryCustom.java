package com.websocket.chatApp.repository;


import com.websocket.chatApp.model.PrivateMessage;
import com.websocket.chatApp.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PrivateMessageRepositoryCustom {
    List<PrivateMessage> findTopNMessages(User sender, User receiver, int limit);
    List<PrivateMessage> findMessagesBefore(User sender, User receiver, LocalDateTime createdAt, int limit);
}
