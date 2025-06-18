package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;

    @Override
    public List<MessageResponse> getChatHistory(LocalDateTime createdAt, int limit) {
        List<Message> messages;
        if (createdAt == null) {
            messages = messageRepository.findTopNMessages(limit);
        } else {
            messages = messageRepository.findMessagesBefore(createdAt, limit);
        }

        // Sort messages by createdAt ASC (oldest of the fetched messages first)
        messages.sort(Comparator.comparing(Message::getCreatedAt));

        // Map to MessageResponse
        List<MessageResponse> messageResponses = new ArrayList<>();
        messages.forEach(message -> {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setUser_id(message.getUserId());
            messageResponse.setUser_name(message.getUser().getUsername());
            messageResponse.setContent(message.getContent());
            messageResponse.setCreated_at(message.getCreatedAt());
            messageResponses.add(messageResponse);
        });

        return messageResponses;
    }
}