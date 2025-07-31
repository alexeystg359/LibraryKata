package com.alexeistegorescu.librarykata.exception;

import lombok.Getter;

@Getter
public class LibraryException extends RuntimeException {

    private final String errorMessage;

    public LibraryException(final String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

}
