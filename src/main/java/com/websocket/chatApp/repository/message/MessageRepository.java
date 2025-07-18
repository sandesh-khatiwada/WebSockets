package com.websocket.chatApp.repository.message;

import com.websocket.chatApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

}