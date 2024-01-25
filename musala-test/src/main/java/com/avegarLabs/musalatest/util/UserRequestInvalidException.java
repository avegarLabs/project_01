package com.avegarLabs.musalatest.util;

public class UserRequestInvalidException extends RuntimeException {

    public UserRequestInvalidException() {
        super("The user request was invalid");
    }

    public UserRequestInvalidException(String message) {
        super(message);
    }
}
