package edu.unc.cs.BookSwap.controller;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.dto.BookSearchResultDto;
import edu.unc.cs.BookSwap.dto.SearchBookDto;
import edu.unc.cs.BookSwap.entity.Book;
import edu.unc.cs.BookSwap.service.AppUserService;
import edu.unc.cs.BookSwap.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserBookController {

    @Autowired
    private BookService bookService;

    // preparing the VIEW for case when user want to add a book
    @GetMapping("/user/book/add")
    public String userAddBook(Model model, Principal principal) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);
        return "book_add";
    }

    // handler method to hand add a book request
    @PostMapping("/user/book/save")
    public String userSaveBook(@Valid @ModelAttribute("book") BookDto bookDto,
                               Authentication authentication,
                               BindingResult result,
                               Model model) {
        String email = authentication.getName();
        try {
            bookService.addBookByUser(bookDto, email);
        } catch (Exception e) {
            result.rejectValue("bookTitle", "Duplicate Entry", e.getMessage());
            if(result.hasErrors()){
                model.addAttribute("book", bookDto);
                return "/book_add";
            }
        }
        return "redirect:/user/book/add?success";
    }

    @GetMapping("/user/book/offered")
    public String listUserBooks(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<Book> userBooks = bookService.findBooksByUserEmail(email);
        model.addAttribute("books", userBooks);
        return "books_view";
    }

    @GetMapping("/user/book/find")
    public String searchBookForm( Model model ) {
        model.addAttribute("searchBookDto", new SearchBookDto());
        return "search_book";
    }

    @PostMapping("/search")
    public String searchBook(@ModelAttribute("searchBookDto") SearchBookDto searchBookDto, Model model) {
        String bookTitle = searchBookDto.getBookTitle();
        List<BookSearchResultDto> results = bookService.searchBooksByTitle(bookTitle);
        model.addAttribute("searchResults", results);
        model.addAttribute("resultCount", results.size());
        return "search_book_result";
    }
}
