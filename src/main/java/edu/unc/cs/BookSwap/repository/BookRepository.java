package edu.unc.cs.BookSwap.repository;

import edu.unc.cs.BookSwap.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // custom JPA function
    Book findByBookTitle(String bookTitle);
}
