package com.bookstore.service;

import java.util.List;

import com.bookstore.exception.BookstoreException;
import com.bookstore.model.Book;

public interface BookService {
    public List<Book> getAllBooks() throws BookstoreException;
    public Book getBookById(Integer id) throws BookstoreException;
    public Book addBook(Book book) throws BookstoreException;
    public Book updateBook(Integer id, Book book) throws BookstoreException;
    public void deleteBook(Integer id) throws BookstoreException;
}
