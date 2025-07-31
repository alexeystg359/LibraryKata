package com.alexeistegorescu.librarykata.service;

import com.alexeistegorescu.librarykata.domain.Book;

import java.util.List;

public interface LibraryService {

    List<Book> getAllAvailableBooks();

    Long add(Book book);

    Book borrow(Long id);

    List<Book> findUserBooks();

    Long returnBook(Long id);

}
