package com.auction.app.utils.exception;

public class WrongTypeEntityException extends RuntimeException{
    public WrongTypeEntityException(String message) {
        super(message);
    }
}
