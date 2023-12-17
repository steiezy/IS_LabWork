package com.lab.airbnb.exception;

public class UserAlreadyExistException extends Exception{
    String key = "user";
    String message = "User already exist";

    @Override
    public String getMessage() {
        return message;
    }

    public String getKey() {
        return key;
    }
}
