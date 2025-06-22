package com.websocket.chatApp.mapper;

import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toDTO(Message message){

        MessageResponse messageDTO= new MessageResponse();
        messageDTO.setMessage_id(message.getMessage_id());
        messageDTO.setUser_name(message.getUser().getUsername());
        messageDTO.setUser_id(message.getUser().getUser_id());
        messageDTO.setContent(message.getContent());
        messageDTO.setCreated_at(message.getCreatedAt());

        return messageDTO;
    }

    public Message toEntity(MessageRequest messageDTO, User user) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setUser(user);
        return message;
    }
}
