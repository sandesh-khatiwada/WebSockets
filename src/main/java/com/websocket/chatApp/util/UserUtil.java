package com.websocket.chatApp.util;

import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public String getUserIdFromUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with the provided username"));
        return user.getUser_id().toString();
    }
}
