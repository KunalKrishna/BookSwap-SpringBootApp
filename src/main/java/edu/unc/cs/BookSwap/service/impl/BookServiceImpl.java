package edu.unc.cs.BookSwap.service.impl;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.dto.BookSearchResultDto;
import edu.unc.cs.BookSwap.entity.Book;
import edu.unc.cs.BookSwap.entity.User;
import edu.unc.cs.BookSwap.entity.UserBook;
import edu.unc.cs.BookSwap.exceptions.BookAlreadyOwnedException;
import edu.unc.cs.BookSwap.exceptions.ResourceNotFoundException;
import edu.unc.cs.BookSwap.mapper.BookMapper;
import edu.unc.cs.BookSwap.repository.BookRepository;
import edu.unc.cs.BookSwap.repository.UserBookRepository;
import edu.unc.cs.BookSwap.repository.UserRepository;
import edu.unc.cs.BookSwap.service.BookService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    //@Autowired
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private UserBookRepository userBookRepository;

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

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books
                .stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(Long bid, BookDto updatedBook) {
        Book book = bookRepository.findById(bid)
                .orElseThrow(() -> new RuntimeException("Book does not exist for bid : " + bid));

        book.setBookTitle(updatedBook.getBookTitle());
        book.setBookAuthor(updatedBook.getBookAuthor());
//        book.setBookGenre(updatedBook.getBookGenre());
//        book.setBookISBN(updatedBook.getBookISBN());
//        book.setBookEdition(updatedBook.getBookEdition());
//        book.setPublicationYear(updatedBook.getPublicationYear());

        Book savedBook = bookRepository.save(book);

        return BookMapper.mapToBookDto(savedBook);
    }

    @Override
    public void deleteBook(Long bid) {
        Book candidateBook = bookRepository.findById(bid)
                .orElseThrow(() -> new ResourceNotFoundException("No book found for bid : " + bid));
        bookRepository.delete(candidateBook);
    }


    @Override
    public BookDto getBookByTitle(String bookTitle) {
        Optional<Book> book = bookRepository.findByBookTitle(bookTitle);
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book not found with title: " + bookTitle);
        }
        return BookMapper.mapToBookDto(book.get());
    }

    BookDto findBookDtoByTitleAndUserEmail(String bookTitle, String email) {
        Optional<BookDto> exitingBookDto = bookRepository.findBookDtoByTitleAndUserEmail(bookTitle,email);
        return exitingBookDto.orElse(null);
    }

    @Override
    @Transactional
    public BookDto addBookByUser(BookDto bookDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email : "+email));

        Book book = bookRepository.findByBookTitle(bookDto.getBookTitle())
                .orElse(null);

        if (book == null) {
            // Case 1: Book doesn't exist, create new book and ownership
            book = BookMapper.mapToBook(bookDto);
            book = bookRepository.save(book);
        } else {
            // Case 2: Book exists, check if user already owns it
            if (userBookRepository.existsByUserAndBook(user, book)) {
                throw new BookAlreadyOwnedException("You already own this book");
            }
        }

        // Create new ownership entry
        UserBook userBook = new UserBook(user, book);
        try {
            // TRIGGER
            userBookRepository.save(userBook);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof SQLException &&
                    e.getCause().getMessage().contains("User cannot own more than 7 books")) {
                throw new IllegalArgumentException("You cannot own more than 7 books.");
            }
            throw e;
        }

        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<Book> findBooksByUserEmail(String email) {
        List<Book> books =  bookRepository.findBooksByUserEmail(email);
        System.out.println("Found " + books.size() + " books");
        return  books;
    }

    @Override
    public BookDto getBookByTitleForGivenUser(String bookTitle, String email) {
        // Step 1: Check if the book exists
        Book book = bookRepository.findByBookTitle(bookTitle)
                .orElse(null);

        // Step 2: Get the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));

        // Step 3: Check if the user owns the book
        UserBook userBook = userBookRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new ResourceNotFoundException("Book " + bookTitle + " is not owned by user " + email));

        // If we've reached here, the book exists and is owned by the user
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<BookSearchResultDto> searchBooksByTitle(String bookTitle) {
        return bookRepository.findBooksByTitleWithOwners(bookTitle);
    }

    @Override
    @Transactional
    public boolean deleteBookForUser(Long bookId, String email) {
        UserBook userBook = userBookRepository.findByBookBidAndUserEmail(bookId, email);
        if (userBook != null) {
            userBookRepository.delete(userBook);
            // Only delete the book if no other user owns it
            if (userBookRepository.countByBookBid(bookId) == 0) {
                bookRepository.deleteById(bookId);
            }
            return true;
        }
        return false;
    }

}
