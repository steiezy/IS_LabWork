package com.lab.airbnb.exception;

public class HouseNotExistException extends Exception{
    String message = "House not exist";

    @Override
    public String getMessage() {
        return message;
    }
}
