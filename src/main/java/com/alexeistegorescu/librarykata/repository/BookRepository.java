package com.alexeistegorescu.librarykata.repository;

import com.alexeistegorescu.librarykata.domain.BookStatus;
import com.alexeistegorescu.librarykata.entity.BookEntity;
import com.alexeistegorescu.librarykata.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByBookStatus(BookStatus bookStatus);

    Optional<BookEntity> findByIdAndBookStatus(Long id, BookStatus status);

    List<BookEntity> findByBorrower(UserEntity user);

    Optional<BookEntity> findByIdAndBorrower(Long id, UserEntity user);

}
