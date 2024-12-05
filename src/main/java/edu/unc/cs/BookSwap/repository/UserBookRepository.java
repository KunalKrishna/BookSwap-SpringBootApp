package edu.unc.cs.BookSwap.repository;

import edu.unc.cs.BookSwap.entity.Book;
import edu.unc.cs.BookSwap.entity.User;
import edu.unc.cs.BookSwap.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    Optional<UserBook> findByUserAndBook(User user, Book book);

    boolean existsByUserAndBook(User user, Book book);

}