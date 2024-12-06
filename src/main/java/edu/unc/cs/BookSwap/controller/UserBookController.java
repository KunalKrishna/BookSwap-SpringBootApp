package edu.unc.cs.BookSwap.controller;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.dto.BookSearchResultDto;
import edu.unc.cs.BookSwap.dto.SearchBookDto;
import edu.unc.cs.BookSwap.entity.Book;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UserBookController {

    @Autowired
    private BookService bookService;

    //********************** ADD : Book Details  *****************************

    // preparing the VIEW for case when user want to add a book
    @GetMapping("/user/book/add")
    public String showAddBookForm(Model model, Principal principal) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);
        return "book_add";
    }

    // handler method to hand add a book request
    @PostMapping("/user/book/save")
    public String addBook(@Valid @ModelAttribute("book") BookDto bookDto,
                          BindingResult result,
                          Authentication authentication,
                          Model model) {
        String email = authentication.getName();
        if (result.hasErrors()) {
            return "book_add";
        }
        try {
            bookService.addBookByUser(bookDto, email);
        } catch (Exception e) {
            result.rejectValue("bookTitle", "Duplicate Entry", e.getMessage());
            if (result.hasErrors()) {
                model.addAttribute("book", bookDto);
                return "/book_add";
            }
        }
        return "redirect:/user/book/add?success";
    }

    //********************** EDIT : Book Details  *****************************

    // preparing the VIEW
    @GetMapping("/user/book/edit")
    public String showEditBookForm(@RequestParam Long bookId, Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        BookDto bookDto = bookService.getBookForEdit(bookId, userEmail);

        if (bookDto == null) {
            // Handle the case where the book doesn't exist or doesn't belong to the user
            return "redirect:/user/dashboard?error";
        }

        model.addAttribute("book", bookDto);
        return "book_update";
    }

    // UPDATING the book details
    @PostMapping("/user/book/update")
    public String updateBook(@ModelAttribute("book") @Valid BookDto bookDto,
                             BindingResult result,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "book_update";
        }

        String userEmail = authentication.getName();
        bookService.updateBookByUser(bookDto, userEmail);

        redirectAttributes.addFlashAttribute("successUpdateMessage", "Book updated successfully!");

        return "redirect:/user/book/offered";
    }


    //********************** SEARCH a Book  *****************************
    @GetMapping("/user/book/find")
    public String searchBookForm(Model model) {
        model.addAttribute("searchBookDto", new SearchBookDto());
        return "search_book";
    }

    @PostMapping("/search")
    public String searchBook(@Valid @ModelAttribute("searchBookDto") SearchBookDto searchBookDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Empty title not allowed!");
            return "search_book";
        }
        String bookTitle = searchBookDto.getBookTitle();
        List<BookSearchResultDto> results = bookService.searchBooksByTitle(bookTitle);
        model.addAttribute("searchResults", results);
        model.addAttribute("resultCount", results.size());
        return "search_book_result";
    }

    //********************** LIST All Books Offered by the User **************************
    @GetMapping("/user/book/offered")
    public String listUserBooks(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<Book> userBooks = bookService.findBooksByUserEmail(email);
        model.addAttribute("books", userBooks);
        return "books_view";
    }


    //********************** DELETE a Book *****************************
    @PostMapping("/user/book/delete")
    public String deleteBook(
            @RequestParam Long bookId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        boolean deleted = bookService.deleteBookForUser(bookId, email);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successDeleteMessage", "Book deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete the book.");
        }
        return "redirect:/user/book/offered";
    }

}
