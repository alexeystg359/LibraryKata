package com.alexeistegorescu.librarykata.controller;

import com.alexeistegorescu.librarykata.domain.Book;
import com.alexeistegorescu.librarykata.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/book")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/all")
    public List<Book> getAllAvailableBooks() {
        return libraryService.getAllAvailableBooks();
    }

    @PostMapping("/add")
    public Long add(@RequestBody @Valid final Book book) {
        return libraryService.add(book);
    }

    @PostMapping("/borrow")
    public Book borrow(@RequestParam final Long id) {
        return libraryService.borrow(id);
    }

    @GetMapping("/my")
    public List<Book> findMyBooks() {
        return libraryService.findUserBooks();
    }

    @DeleteMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam final Long id) {
        final Long returnedBookId = libraryService.returnBook(id);
        return ResponseEntity.accepted().body("The book with id " + returnedBookId + " was returned");
    }
}
