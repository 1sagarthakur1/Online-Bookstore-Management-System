package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public Optional<Book> findByTitleContainingIgnoreCase(String title);
//    public List<Book> findByAuthorContainingIgnoreCase(String author);
//    public List<Book> findByGenreContainingIgnoreCase(String genre);
}
