package com.jumia.services.customers.exception;

public class NumberTooSmallException extends Exception {
    public NumberTooSmallException(String message) {
        super(message);
    }
}