package com.lab.airbnb.exception;

public class UserPhoneNumAlreadyExistException extends UserAlreadyExistException{
    String message = "Phone number already exist";

    String key = "phoneNum";

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getKey() {
        return key;
    }
}
