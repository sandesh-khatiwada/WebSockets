package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;

    private final EntityManager entityManager;


    @Override
    public List<MessageResponse> getChatHistory(Long lastMessageId,int limit) {

        List<Message> messages;
        if (lastMessageId == null) {
            // Fetch the most recent N messages
            TypedQuery<Message> query = entityManager.createQuery(
                    "SELECT m FROM Message m ORDER BY m.createdAt DESC",
                    Message.class
            );
            query.setMaxResults(limit);
            messages = query.getResultList();
        } else {
            // Fetch the next batch of messages before lastMessageId
            TypedQuery<Message> query = entityManager.createQuery(
                    "SELECT m FROM Message m WHERE m.message_id < :lastMessageId ORDER BY m.createdAt DESC",
                    Message.class
            );
            query.setParameter("lastMessageId", lastMessageId);
            query.setMaxResults(limit);
            messages = query.getResultList();
        }

        // Reorder messages to createdAt ASC (oldest of the fetched messages first)
        messages.sort(Comparator.comparing(Message::getCreatedAt));

        List<MessageResponse> messageResponses = new ArrayList<>();

        messages.forEach(message -> {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setUser_id(message.getUserId());
            messageResponse.setUser_name(message.getUser().getUsername());
            messageResponse.setContent(message.getContent());
            messageResponses.add(messageResponse);
        });


        return messageResponses;

    }
}
