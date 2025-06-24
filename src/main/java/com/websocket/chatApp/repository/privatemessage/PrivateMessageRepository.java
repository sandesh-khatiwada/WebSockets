package com.websocket.chatApp.repository.privatemessage;

import com.websocket.chatApp.model.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> , PrivateMessageRepositoryCustom {
}
