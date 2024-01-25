package com.avegarLabs.musalatest.util;

public class PostRequestInvalidException extends RuntimeException {

    public PostRequestInvalidException() {
        super("The post request was invalid");
    }

    public PostRequestInvalidException(String message) {
        super(message);
    }
}
