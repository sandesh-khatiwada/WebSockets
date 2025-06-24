package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.dto.PrivateMessageResponse;
import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.model.PrivateMessage;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.MessageRepository;
import com.websocket.chatApp.repository.PrivateMessageRepository;
import com.websocket.chatApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final PrivateMessageRepository privateMessageRepository;
    private final UserRepository userRepository;

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
            messageResponse.setUser_id(message.getUser().getUser_id());
            messageResponse.setUser_name(message.getUser().getUsername());
            messageResponse.setContent(message.getContent());
            messageResponse.setCreated_at(message.getCreatedAt());
            messageResponse.setMessage_id(message.getMessage_id());
            messageResponses.add(messageResponse);
        });

        return messageResponses;
    }


    @Override
    public List<PrivateMessageResponse> getPrivateChatHistory(String receiverUsername, String senderUsername, int limit,  LocalDateTime createdAt){

        User sender  = userRepository.findByUsername(senderUsername).orElseThrow(()->new UsernameNotFoundException("Sender with provided username does not exist"));

        User receiver  = userRepository.findByUsername(receiverUsername).orElseThrow(()->new UsernameNotFoundException("Receiver with provided username does not exist"));

        List<PrivateMessage> messages;
        if (createdAt == null) {
            messages = privateMessageRepository.findTopNMessages(sender, receiver, limit);
        } else {
            messages = privateMessageRepository.findMessagesBefore(sender, receiver, createdAt, limit);
        }

        // Sort messages by createdAt ASC (oldest of the fetched messages first)
        messages.sort(Comparator.comparing(PrivateMessage::getCreatedAt));

        // Map to MessageResponse
        List<PrivateMessageResponse> privateMessageResponses = new ArrayList<>();
        messages.forEach(message -> {
            PrivateMessageResponse messageResponse = new PrivateMessageResponse();
            messageResponse.setSender_id(message.getSender().getUser_id());
            messageResponse.setReceiver_id(message.getReceiver().getUser_id());
            messageResponse.setContent(message.getContent());
            messageResponse.setMessage_id(message.getMessage_id());
            messageResponse.setCreated_at(message.getCreatedAt());
            messageResponse.setSender_username(message.getSender().getUsername());
            privateMessageResponses.add(messageResponse);

        });

        return privateMessageResponses;


    }

}