package com.alexeistegorescu.librarykata.domain;

import jakarta.validation.constraints.NotBlank;

public record Book(Long id, @NotBlank String name, @NotBlank String author) {
}
