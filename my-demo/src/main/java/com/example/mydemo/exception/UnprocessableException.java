package com.example.mydemo.exception;

public class UnprocessableException extends RuntimeException {
    public UnprocessableException(String message) {
        super(message);
    }
}
