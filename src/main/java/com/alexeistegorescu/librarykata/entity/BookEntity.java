package com.alexeistegorescu.librarykata.entity;


import com.alexeistegorescu.librarykata.domain.BookStatus;
import com.alexeistegorescu.librarykata.security.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Not making name/author unique since library can have many copies of the same book
    private String name;

    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus bookStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity borrower;


}
