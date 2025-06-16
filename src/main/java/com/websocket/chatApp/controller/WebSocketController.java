package com.websocket.chatApp.controller;

import com.websocket.chatApp.model.Message;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.MessageRepository;
import com.websocket.chatApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message sendMessage(String message){
//        System.out.println("Received message: "+message);
//        Message msg = new Message();
//        msg.setContent("Message: "+message);
//
//
//        // Dummy user
//        return msg;

        System.out.println("Received message: "+message);
        User user = userRepository.findById(1L).get();

        Message msg = new Message();

        System.out.println(message);
        msg.setContent(message);
        msg.setUser(user);

        messageRepository.save(msg);

        return msg;
    }
}
