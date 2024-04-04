package com.ardb.spring_test.exception;

public class ProductAlreadyHaveException extends RuntimeException {
    public ProductAlreadyHaveException(String message) {
        super(message);
    }
}
