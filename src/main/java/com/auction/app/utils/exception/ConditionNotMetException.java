package com.auction.app.utils.exception;

public class ConditionNotMetException extends RuntimeException{
    public ConditionNotMetException(String message) {
        super(message);
    }
}
