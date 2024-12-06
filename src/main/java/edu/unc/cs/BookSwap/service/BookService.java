package edu.unc.cs.BookSwap.service;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.dto.BookSearchResultDto;
import edu.unc.cs.BookSwap.entity.Book;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto getBookById(Long bid);

    List<BookDto> getAllBooks();

    BookDto updateBook(Long bid, BookDto updatedBook);

    void deleteBook(Long bid);

    BookDto getBookByTitle(String bookTitle);

    BookDto addBookByUser(BookDto bookDto, String email);

    void updateBookByUser(BookDto bookDto, String userEmail);

    public List<Book> findBooksByUserEmail(String email);

    BookDto getBookByTitleForGivenUser(String bookTitle, String email);

    BookDto getBookForEdit(Long bookId, String userEmail);

    List<BookSearchResultDto> searchBooksByTitle(String bookTitle);

    boolean deleteBookForUser(Long bookId, String email);
}
