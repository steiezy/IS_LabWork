package com.lab.airbnb.exception;

public class UserAlreadyExistException extends Exception{
    String message = "User already exist";

    @Override
    public String getMessage() {
        return message;
    }
}
