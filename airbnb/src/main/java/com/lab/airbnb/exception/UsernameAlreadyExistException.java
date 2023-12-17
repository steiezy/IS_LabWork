package com.lab.airbnb.exception;

public class UsernameAlreadyExistException extends UserAlreadyExistException{
    String message = "Username already exist";

    String key="username";

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getKey() {
        return key;
    }
}
