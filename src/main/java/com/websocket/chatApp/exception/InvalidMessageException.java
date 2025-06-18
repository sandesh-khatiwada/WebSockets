package com.websocket.chatApp.exception;

public class InvalidMessageException extends RuntimeException{
    public InvalidMessageException(String message) {
        super(message);
    }
}
