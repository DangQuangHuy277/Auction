package com.auction.app.exception;

public class WrongTypeEntityException extends RuntimeException{
    public WrongTypeEntityException(String message) {
        super(message);
    }
}
