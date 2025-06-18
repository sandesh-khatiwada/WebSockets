package com.websocket.chatApp.exception;

import com.websocket.chatApp.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST,
                "Invalid request body",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST,
                "Invalid request",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST,
                "User not found",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMessageException.class)
    public ResponseEntity<APIResponse<Object>> handleInvalidMessageException(InvalidMessageException ex) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST,
                "Invalid message",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse<Object>> handleRuntimeException(RuntimeException ex) {

        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleGeneralException(Exception ex) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
