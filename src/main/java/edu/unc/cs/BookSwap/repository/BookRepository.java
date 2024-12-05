package edu.unc.cs.BookSwap.repository;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.dto.BookSearchResultDto;
import edu.unc.cs.BookSwap.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // custom JPA function
    Optional<Book> findByBookTitle(String bookTitle);

    //@Query("SELECT b FROM Book b JOIN UserBook ub ON b.id = ub.book.id JOIN User u ON u.id = ub.user.id WHERE u.email = :email")
    @Query("SELECT b FROM Book b JOIN UserBook ub ON b.bid = ub.book.bid JOIN User u ON u.id = ub.user.id WHERE u.email = :email")
    List<Book> findBooksByUserEmail(String email);

    @Query("SELECT new edu.unc.cs.BookSwap.dto.BookDto(b.bid, b.bookTitle, b.bookAuthor) " +
            "FROM Book b " +
            "JOIN UserBook ub ON b.bid = ub.book.bid " +
            "JOIN User u ON u.id = ub.user.id " +
            "WHERE b.bookTitle = :bookTitle AND u.email = :email")
    Optional<BookDto> findBookDtoByTitleAndUserEmail(String bookTitle, String email);

    @Query("SELECT NEW edu.unc.cs.BookSwap.dto.BookSearchResultDto(b.bookTitle, b.bookAuthor, u.email) " +
            "FROM Book b " +
            "JOIN UserBook ub ON b.bid = ub.book.bid " +
            "JOIN User u ON u.id = ub.user.id " +
            "WHERE LOWER(b.bookTitle) = LOWER(:bookTitle)")
    List<BookSearchResultDto> findBooksByTitleWithOwners(@Param("bookTitle") String bookTitle);
}
