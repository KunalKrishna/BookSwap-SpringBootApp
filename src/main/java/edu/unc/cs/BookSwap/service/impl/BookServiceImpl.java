package edu.unc.cs.BookSwap.service.impl;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.entity.Book;
import edu.unc.cs.BookSwap.exceptions.ResourceNotFoundException;
import edu.unc.cs.BookSwap.mapper.BookMapper;
import edu.unc.cs.BookSwap.repository.BookRepository;
import edu.unc.cs.BookSwap.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    //@Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = BookMapper.mapToBook(bookDto);
        Book savedBook = bookRepository.save(book);
        return BookMapper.mapToBookDto(savedBook);
    }

    @Override
    public BookDto getBookById(Long bid) {
        if (bid == null) {
            // Handle the case where the ID is null (e.g., throw an exception or return a default value)
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Book book = bookRepository.findById(bid)
                .orElseThrow(()->
                        new ResourceNotFoundException("book doesn't exist with given id : "+ bid));
        return BookMapper.mapToBookDto(book);
    }
}
