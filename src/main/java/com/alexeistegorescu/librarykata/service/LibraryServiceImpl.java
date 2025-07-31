package com.alexeistegorescu.librarykata.service;

import com.alexeistegorescu.librarykata.domain.Book;
import com.alexeistegorescu.librarykata.domain.BookStatus;
import com.alexeistegorescu.librarykata.entity.BookEntity;
import com.alexeistegorescu.librarykata.exception.LibraryException;
import com.alexeistegorescu.librarykata.repository.BookRepository;
import com.alexeistegorescu.librarykata.security.entity.UserEntity;
import com.alexeistegorescu.librarykata.security.service.UserService;
import com.alexeistegorescu.librarykata.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    private final UserService userService;

    @Override
    public List<Book> getAllAvailableBooks() {
        return bookRepository.findByBookStatus(BookStatus.AVAILABLE)
                .stream()
                .map(book -> new Book(book.getId(), book.getName(), book.getAuthor()))
                .toList();
    }

    @Override
    public Long add(final Book book) {
        final BookEntity bookEntity = BookEntity.builder()
                .name(book.name())
                .author(book.author())
                .bookStatus(BookStatus.AVAILABLE)
                .build();
        return bookRepository.save(bookEntity).getId();
    }

    @Override
    public Book borrow(final Long id) {
        final Optional<BookEntity> bookEntity = bookRepository.findByIdAndBookStatus(id, BookStatus.AVAILABLE);
        if (bookEntity.isPresent()) {
            BookEntity borrowed = bookEntity.get();
            borrowed.setBookStatus(BookStatus.BORROWED);
            borrowed.setBorrower(getAuthenticatedUser());
            bookRepository.save(borrowed);
            return new Book(borrowed.getId(), borrowed.getName(), borrowed.getAuthor());
        }
        throw new LibraryException("This book is currently not available");
    }

    @Override
    public List<Book> findUserBooks() {
        return bookRepository.findByBorrower(getAuthenticatedUser())
                .stream()
                .map(book -> new Book(book.getId(), book.getName(), book.getAuthor()))
                .toList();
    }

    @Override
    public Long returnBook(Long id) {
        final UserEntity authenticatedUser = getAuthenticatedUser();
        final Optional<BookEntity> book = bookRepository.findByIdAndBorrower(id, authenticatedUser);
        if (book.isPresent()) {
            final BookEntity returnedBook = book.get();
            returnedBook.setBorrower(null);
            returnedBook.setBookStatus(BookStatus.AVAILABLE);
            bookRepository.save(returnedBook);
            return returnedBook.getId();
        }
        throw new LibraryException("You did not borrow a book with id " + id);
    }

    private UserEntity getAuthenticatedUser() {
        return userService.findById(getAuthenticatedUserId()).orElseThrow(() -> new LibraryException("User not found"));
    }

    private static Long getAuthenticatedUserId() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }
        throw new LibraryException("User not authenticated");
    }
}
