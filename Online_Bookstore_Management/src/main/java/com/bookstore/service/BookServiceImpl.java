package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.exception.BookstoreException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() throws BookstoreException{
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Integer id) throws BookstoreException{
        return bookRepository.findById(id).orElseThrow(() -> new BookstoreException("Book not found with id: " + id));
    }

    @Override
    public Book addBook(Book book) throws BookstoreException{
    	Optional<Book> book2 = bookRepository.findByTitleContainingIgnoreCase(book.getTitle());
    	if(book2.isPresent()) {
    		throw new BookstoreException("This book all ready present change titel");
    	}
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Integer id, Book book) throws BookstoreException{
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setDescription(book.getDescription());
        existingBook.setPrice(book.getPrice());
        existingBook.setQuantity(book.getQuantity());
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Integer id) throws BookstoreException{
        bookRepository.deleteById(id);
    }
}
