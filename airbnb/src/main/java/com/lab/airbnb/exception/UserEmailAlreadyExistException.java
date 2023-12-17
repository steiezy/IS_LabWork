package com.lab.airbnb.exception;

public class UserEmailAlreadyExistException extends UserAlreadyExistException {
    String message = "email already exist";
    String key = "email";

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getKey() {
        return key;
    }
}
