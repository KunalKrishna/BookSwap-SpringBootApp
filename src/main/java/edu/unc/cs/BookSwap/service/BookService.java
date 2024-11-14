package edu.unc.cs.BookSwap.service;

import edu.unc.cs.BookSwap.dto.BookDto;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    BookDto getBookById(Long bid);
//    BookDto updateBook(Long bid);
//    BookDto deleteBook(Long bid);
}
