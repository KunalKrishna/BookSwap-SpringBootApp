package edu.unc.cs.BookSwap.controller;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.entity.Book;
import edu.unc.cs.BookSwap.service.BookService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    //@Autowired
    private BookService bookService;

    // REST API for creating a book : http://localhost:8080/api/books
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBookDto = bookService.createBook(bookDto);
        return  new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    // REST API for Retrieving a book : http://localhost:8080/api/books/1
    // PS : @PathVariable not @PathParam
    @GetMapping("{bid}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("bid") Long bid) {
        if (bid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book ID cannot be null");
        }
        BookDto bookDto = bookService.getBookById(bid);
        //return ResponseEntity.ok(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.FOUND);
    }

    // REST API for Retrieving all books : http://localhost:8080/api/books/
    @GetMapping("/")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks,HttpStatus.FOUND);
//        return ResponseEntity.ok(allBooks);
    }

    // REST API for Updating a book : http://localhost:8080/api/books/1
    @PutMapping("{bid}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long bid,
                                              @RequestBody BookDto updatedBook) {
        BookDto bookDto = bookService.updateBook(bid, updatedBook);
        return new ResponseEntity<>(bookDto, HttpStatus.ACCEPTED);
//        return ResponseEntity.ok(bookDto);
    }
}
