package com.websocket.chatApp.mapper;

import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.util.EncryptionUtil;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toDTO(Message message){


            MessageResponse messageDTO = new MessageResponse();
            messageDTO.setMessageId(message.getMessageId());
            messageDTO.setSenderUsername(message.getUser().getUsername());
            messageDTO.setUserId(message.getUser().getUserId());
            try {
                messageDTO.setContent(EncryptionUtil.decrypt(message.getContent()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            messageDTO.setCreatedAt(message.getCreatedAt());

            return messageDTO;

    }

    public Message toEntity(MessageRequest messageDTO, User user)  {

            Message message = new Message();

            try {
                message.setContent(EncryptionUtil.encrypt(messageDTO.getContent()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            message.setUser(user);
            return message;

    }
}
