package com.deepak.auth.app.utils;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 8928755980632490462L;

    private String errorCode;

    private Object[] args;

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, String errorCode, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = Stream.of(args).toArray();
    }
}
