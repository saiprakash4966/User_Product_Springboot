package com.userproduct.exception;

public class NoProductsFoundException extends RuntimeException {
    public NoProductsFoundException(String message) {
        super(message);
    }
}
