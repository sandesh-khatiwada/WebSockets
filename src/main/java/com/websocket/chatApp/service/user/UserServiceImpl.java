package com.websocket.chatApp.service.user;

import com.websocket.chatApp.dto.UserResponse;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> response = new ArrayList<>();

        users.forEach((user -> {

            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(user.getUsername());
            response.add(userResponse);
        }));

        return response;

    }
}
