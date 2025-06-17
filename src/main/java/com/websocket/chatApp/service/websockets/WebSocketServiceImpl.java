package com.websocket.chatApp.service.websockets;

import com.websocket.chatApp.mapper.MessageMapper;
import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.MessageRepository;
import com.websocket.chatApp.repository.UserRepository;
import com.websocket.chatApp.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketServiceImpl implements  WebSocketService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final JwtUtil jwtUtil;


    @Override
    public MessageResponse sendMessage(MessageRequest inputMessage) {

        System.out.println("Received message: " + inputMessage);

        String username = jwtUtil.getUsernameFromToken(inputMessage.getToken());

        User user = userRepository.findByUsername(username);

        if(user==null){
            throw new IllegalArgumentException("User not found");
        }


        Message msg= messageMapper.toEntity(inputMessage, user);


        Message savedMessage= messageRepository.save(msg);

        MessageResponse responseMessage = messageMapper.toDTO(savedMessage);

        System.out.println("Sent messatge: "+responseMessage);

        return responseMessage;
    }
}
