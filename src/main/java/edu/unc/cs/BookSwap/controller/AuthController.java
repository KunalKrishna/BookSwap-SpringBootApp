package edu.unc.cs.BookSwap.controller;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.dto.UserDto;
import edu.unc.cs.BookSwap.entity.Book;
import edu.unc.cs.BookSwap.entity.User;
import edu.unc.cs.BookSwap.exceptions.ResourceNotFoundException;
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
public class AuthController {

    private AppUserService appUserService;
    @Autowired
    private BookService bookService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // handler method to handle home page request
    @GetMapping({"","/", "/home","/index"})
    public String home(){
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = appUserService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        appUserService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = appUserService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Principal principal) {
        String email = principal.getName(); // Get the logged-in user's email
        model.addAttribute("email", email); // Pass email to the view
        return "user_dashboard";
    }


    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // handler method to handle login request
    @GetMapping("/error")
    public String error(){
        return "404";
    }

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
        BookDto existingBook = null;
        String email = authentication.getName();
        try {
            //first check, if book exists ? If yes, does it exist under this email?
                //if answer to both question is YES : Raise error : "There is already a book added with the same Title"
            existingBook = bookService.getBookByTitleForGivenUser(bookDto.getBookTitle(), email);
        } catch (ResourceNotFoundException e) {
                // if answer to either of two question is NO then add book for this email : GOOD FOR ADDING
            bookService.addBookByUser(bookDto, email);
            return "redirect:/user/book/add?success";

//            bookService.createBook(bookDto);
//            return "redirect:/user/dashboard?success";
        }

        if(existingBook != null && existingBook.getBookTitle() != null && !existingBook.getBookTitle().isEmpty()){
            result.rejectValue("bookTitle", "Duplicate Entry",
                    "You have already added a book with the same Title");
        }

        if(result.hasErrors()){
            model.addAttribute("book", bookDto);
            return "/book_add";
        }

        bookService.addBookByUser(bookDto, email);
        return "redirect:/user/book/add?success";
    }

    @GetMapping("/user/book/offered")
    public String listUserBooks(Authentication authentication, Model model) {
        if (authentication == null) {
            System.out.println("User is not authenticated");
            return "redirect:/login";
        }
        String email = authentication.getName();
        List<Book> userBooks = bookService.findBooksByUserEmail("big@123.com");
        model.addAttribute("books", userBooks);
        return "books_view";
    }

    @GetMapping("/user/book/find")
    public String searchBookForm( Model model ) {
        String bookTitle = "";
        model.addAttribute("bookTitle", bookTitle);
        return "search_book";
    }

//    @PostMapping("/search")
//    public String searchBook(@ModelAttribute("bookTitle") String bookTitle) {
//        // Process the bookTitle here
//        return "search_book_result";
//    }
}