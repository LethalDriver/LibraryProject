package com.example.libraryproject.exception;

public class NoBookInStockException extends RuntimeException {
    public NoBookInStockException(String s) {
        super(s);
    }
}
