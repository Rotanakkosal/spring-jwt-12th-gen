package com.levi.springjwt.exception;

public class DataBadRequestException extends RuntimeException {
    public DataBadRequestException(String message) {
        super(message);
    }
}
