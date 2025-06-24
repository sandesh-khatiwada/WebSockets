package com.websocket.chatApp.config;

import com.websocket.chatApp.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:5500", "http://localhost:4200", "http://localhost:63719", "*")
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        String authHeader = request.getHeaders().getFirst("Authorization");
                        String token = null;

                        // Check Authorization header first
                        if (authHeader != null && authHeader.startsWith("Bearer ")) {
                            token = authHeader.substring(7);

                        } else {
                            // Fallback to query parameter
                            String query = request.getURI().getQuery();
                            if (query != null && query.contains("access_token=")) {
                                token = query.split("access_token=")[1].split("&")[0];
                                try {
                                    token = URLDecoder.decode(token, StandardCharsets.UTF_8.name());

                                } catch (Exception e) {

                                    throw new IllegalArgumentException("Invalid query token");
                                }
                            }
                        }

                        if (token == null) {
                            logger.error("Missing or invalid Authorization header or query parameter");
                            throw new IllegalArgumentException("Missing or invalid Authorization");
                        }

                        String username = jwtUtil.getUsernameFromToken(token);

                        if (username == null || username.isEmpty()) {
                            logger.error("Invalid JWT token: no username found");
                            throw new IllegalArgumentException("Invalid JWT token");
                        }
                        attributes.put("username", username);

                        return new SimplePrincipal(username);
                    }
                })
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // Enable broker for topic, and queue
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");

    }

    class SimplePrincipal implements Principal {
        private final String username;

        SimplePrincipal(String name) {
            this.username = name;
        }

        @Override
        public String getName() {
            return username;
        }
    }
}