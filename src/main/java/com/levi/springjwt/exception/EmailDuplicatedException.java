package com.levi.springjwt.exception;

public class EmailDuplicatedException extends RuntimeException{
    public EmailDuplicatedException(String message){
        super(message);
    }
}
