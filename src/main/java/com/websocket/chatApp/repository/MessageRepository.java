package com.websocket.chatApp.repository;

import com.websocket.chatApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Fetch messages ordered by createdAt DESC (most recent first for selection)
    @Query("SELECT m FROM Message m ORDER BY m.createdAt DESC")
    List<Message> findAllMessagesOrderedByCreatedAtDesc();

    // Fetch messages older than the given message_id, ordered by createdAt DESC
    @Query("SELECT m FROM Message m WHERE m.message_id < :lastMessageId ORDER BY m.createdAt DESC")
    List<Message> findMessagesBefore(@Param("lastMessageId") Long lastMessageId);


}
