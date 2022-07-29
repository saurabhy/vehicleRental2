package com.practice.vehiclebooking.exception;

public class CustomException extends RuntimeException{
    public CustomException(String errorMessage){
        super(errorMessage);
    }
}
