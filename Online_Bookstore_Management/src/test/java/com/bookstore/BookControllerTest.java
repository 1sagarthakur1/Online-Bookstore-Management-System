package com.bookstore;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bookstore.cotroller.BookController;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(new Book(1, "Effective Java", "Joshua Bloch", "A comprehensive guide to programming in Java.", 45, 100), new Book(2, "Clean Code", "Robert C. Martin", "A handbook of agile software craftsmanship.", 50, 75));
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testAddBook() {
        Book book = new Book(5, "Design Patterns", "Erich Gamma", "Elements of reusable object-oriented software.", 40, 120);
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.addBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).addBook(book);
    }

    @Test
    void testUpdateBook_Success() {
        Integer bookId = 1;
        Book book = new Book(4, "Spring in Action", "Craig Walls", "A comprehensive guide to the Spring Framework.", 55, 80);
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.updateBook(bookId, book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).updateBook(bookId, book);
    }

    @Test
    void testUpdateBook_NotFound() {
        Integer bookId = 1;
        Book book = new Book(7, "Head First Java", "Kathy Sierra", "A brain-friendly guide to Java programming.", 35, 150);
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(null);

        ResponseEntity<Book> response = bookController.updateBook(bookId, book);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).updateBook(bookId, book);
    }

    @Test
    void testDeleteBook() {
        Integer bookId = 1;

        bookController.deleteBook(bookId);

        verify(bookService, times(1)).deleteBook(bookId);
    }
}

