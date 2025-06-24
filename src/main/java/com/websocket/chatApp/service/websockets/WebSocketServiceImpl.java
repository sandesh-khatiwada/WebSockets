package com.websocket.chatApp.service.websockets;

import com.websocket.chatApp.dto.PrivateMessageRequest;
import com.websocket.chatApp.dto.PrivateMessageResponse;
import com.websocket.chatApp.exception.InvalidMessageException;
import com.websocket.chatApp.mapper.MessageMapper;
import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.model.PrivateMessage;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.MessageRepository;
import com.websocket.chatApp.repository.PrivateMessageRepository;
import com.websocket.chatApp.repository.UserRepository;
import com.websocket.chatApp.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class WebSocketServiceImpl implements  WebSocketService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final PrivateMessageRepository privateMessageRepository;
    private final MessageMapper messageMapper;
    private final JwtUtil jwtUtil;


    @Override
    public MessageResponse sendMessage(MessageRequest inputMessage) {

        if(inputMessage.getContent()==null || inputMessage.getContent().isEmpty()){
                throw new InvalidMessageException("Message should not be empty");
        }

        System.out.println("Received message: " + inputMessage);

        String username = jwtUtil.getUsernameFromToken(inputMessage.getToken());

        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));



        Message msg= messageMapper.toEntity(inputMessage, user);


        Message savedMessage= messageRepository.save(msg);

        MessageResponse responseMessage = messageMapper.toDTO(savedMessage);

        System.out.println("Sent message: "+responseMessage);

        return responseMessage;
    }

    @Override
    public PrivateMessageResponse sendPrivateMessage(PrivateMessageRequest inputMessage){
        if(inputMessage.getContent()==null || inputMessage.getContent().isEmpty()){
            throw new InvalidMessageException("Message should not be empty");
        }

        System.out.println("Received message: " + inputMessage);

        String username = jwtUtil.getUsernameFromToken(inputMessage.getToken());

        User sender = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        User receiver = userRepository.findByUsername(inputMessage.getReceiverUsername()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        PrivateMessage privateMessage = new PrivateMessage();

        privateMessage.setContent(inputMessage.getContent());
        privateMessage.setSender(sender);
        privateMessage.setReceiver(receiver);
        privateMessage.setCreatedAt(LocalDateTime.now());

        PrivateMessage savedMessage = privateMessageRepository.save(privateMessage);


        PrivateMessageResponse privateMessageResponse = new PrivateMessageResponse();
        privateMessageResponse.setContent(savedMessage.getContent());
        privateMessageResponse.setMessage_id(savedMessage.getMessage_id());
        privateMessageResponse.setSender_id(savedMessage.getSender().getUser_id());
        privateMessageResponse.setReceiver_id(savedMessage.getReceiver().getUser_id());
        privateMessageResponse.setSender_username(savedMessage.getSender().getUsername());
        privateMessageResponse.setReceiver_username(savedMessage.getReceiver().getUsername());
        privateMessageResponse.setCreated_at(savedMessage.getCreatedAt());

        System.out.println("Sent message: "+privateMessageResponse);


        return privateMessageResponse;


    }

}
