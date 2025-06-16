package com.websocket.chatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");  // creates a simple in-memory message broker for subscriptions

        config.setApplicationDestinationPrefixes("/app");  //creates prefix for client messages

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){

        registry.addEndpoint("/ws").
                setAllowedOrigins("http://127.0.0.1:5500").
                withSockJS();

        // specifies websocket endpoint for clients to connect
        // withSockJS() enables fallback support
    }

}
