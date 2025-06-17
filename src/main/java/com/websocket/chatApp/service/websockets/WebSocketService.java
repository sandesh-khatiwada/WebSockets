package com.websocket.chatApp.service.websockets;


import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;

public interface WebSocketService {

    MessageResponse sendMessage(MessageRequest inputMessage);
}
