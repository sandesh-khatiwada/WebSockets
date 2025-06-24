package com.websocket.chatApp.service.websockets;


import com.websocket.chatApp.dto.MessageRequest;
import com.websocket.chatApp.dto.MessageResponse;
import com.websocket.chatApp.dto.PrivateMessageRequest;
import com.websocket.chatApp.dto.PrivateMessageResponse;

public interface WebSocketService {

    MessageResponse sendMessage(MessageRequest inputMessage);
    PrivateMessageResponse sendPrivateMessage(PrivateMessageRequest inputMessage);
}
