package com.websocket.chatApp.service.chat;

import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    MessageRepository messageRepository;


    @Override
    public List<MessageResponse> getChatHistory() {
        List<Message> messages = messageRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
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
