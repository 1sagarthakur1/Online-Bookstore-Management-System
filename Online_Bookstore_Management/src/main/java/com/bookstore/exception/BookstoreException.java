package com.bookstore.exception;

public class BookstoreException extends RuntimeException {
    public BookstoreException() {
        
    }

    public BookstoreException(String message) {
        super(message);
    }
}
