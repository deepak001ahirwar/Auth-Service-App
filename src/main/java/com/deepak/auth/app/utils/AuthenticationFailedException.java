package com.deepak.auth.app.utils;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class AuthenticationFailedException extends RuntimeException{

    private String errorCode;
    
    private Object[] args;
    
    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }
    
    public AuthenticationFailedException(String message, String errorCode, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = Stream.of(args).toArray();
    }
}