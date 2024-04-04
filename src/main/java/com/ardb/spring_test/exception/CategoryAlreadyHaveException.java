package com.ardb.spring_test.exception;

public class CategoryAlreadyHaveException extends RuntimeException {
    public CategoryAlreadyHaveException(String message) {
        super(message);
    }
}
