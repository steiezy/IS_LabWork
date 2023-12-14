package com.lab.airbnb.exception;

public class UserPhoneNumAlreadyExistException extends UserAlreadyExistException{
    String message = "Phone number already exist";

    @Override
    public String getMessage() {
        return message;
    }
}
