package edu.unc.cs.BookSwap.mapper;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.entity.Book;

public class BookMapper {
    public static BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getBid(),
                book.getBookTitle(),
                book.getBookAuthor(),
                book.getBookGenre(),
                book.getBookISBN(),
                book.getPublicationYear(),
                book.getBookEdition()
        );
    }
    public static Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getBid(),
                bookDto.getBookTitle(),
                bookDto.getBookAuthor(),
                bookDto.getBookGenre(),
                bookDto.getBookISBN(),
                bookDto.getPublicationYear(),
                bookDto.getBookEdition()
        );
    }
}
