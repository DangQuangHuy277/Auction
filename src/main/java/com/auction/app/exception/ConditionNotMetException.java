package com.auction.app.exception;

public class ConditionNotMetException extends RuntimeException{
    public ConditionNotMetException(String message) {
        super(message);
    }
}
