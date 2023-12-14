package com.lab.airbnb.exception;

public class UsernameAlreadyExistException extends UserAlreadyExistException{
    String message = "Username already exist";

    @Override
    public String getMessage() {
        return message;
    }
}
