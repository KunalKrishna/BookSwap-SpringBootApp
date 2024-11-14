package edu.unc.cs.BookSwap.service;

import edu.unc.cs.BookSwap.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    BookDto getBookById(Long bid);
    List<BookDto> getAllBooks();

    BookDto updateBook(Long bid, BookDto updatedBook);
//    BookDto deleteBook(Long bid);
}
