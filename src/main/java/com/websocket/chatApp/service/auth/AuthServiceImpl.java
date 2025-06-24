package com.websocket.chatApp.service.auth;

import com.websocket.chatApp.dto.LoginRequest;
import com.websocket.chatApp.dto.LoginResponse;
import com.websocket.chatApp.model.User;
import com.websocket.chatApp.repository.user.UserRepository;
import com.websocket.chatApp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequestDTO) {

        if(Objects.equals(loginRequestDTO.getUsername(), "") || loginRequestDTO.getUsername()==null){

            throw new IllegalArgumentException("Username is required");
        }

        if(Objects.equals(loginRequestDTO.getPassword(), "") || loginRequestDTO.getPassword()==null){
            throw new IllegalArgumentException("Password is required");

        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(loginRequestDTO.getUsername()).orElseThrow(()->new UsernameNotFoundException("User not found with username"+loginRequestDTO.getUsername()));

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setUsername(user.getUsername());
        loginResponse.setToken(jwt);

        return loginResponse;
    }
}
