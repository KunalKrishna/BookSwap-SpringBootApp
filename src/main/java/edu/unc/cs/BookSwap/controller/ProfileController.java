package edu.unc.cs.BookSwap.controller;

import edu.unc.cs.BookSwap.dto.BookDto;
import edu.unc.cs.BookSwap.entity.User;
import edu.unc.cs.BookSwap.service.AppUserService;
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

@Controller
public class ProfileController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("/user/profile/edit")
    public String showUserProfile(Authentication authentication,
                                  Model model) {
        String email = authentication.getName();
        User userByEmail = appUserService.findUserByEmail(email);
        model.addAttribute("user", userByEmail);
        return "user_profile";
    }
//
//    @PostMapping("/updateProfile")
//    public String showDashboard() {
//
//    }
//
////********************** EDIT : Book Details  *****************************
//
//    // preparing the VIEW
//    @GetMapping("/user/book/edit")
//    public String showEditBookForm(@RequestParam Long bookId, Model model, Authentication authentication) {
//        String userEmail = authentication.getName();
//        BookDto bookDto = bookService.getBookForEdit(bookId, userEmail);
//
//        if (bookDto == null) {
//            // Handle the case where the book doesn't exist or doesn't belong to the user
//            return "redirect:/user/dashboard?error";
//        }
//
//        model.addAttribute("book", bookDto);
//        return "book_update";
//    }
//
//    // UPDATING the book details
//    @PostMapping("/user/book/update")
//    public String updateBook(@ModelAttribute("book") @Valid BookDto bookDto,
//                             BindingResult result,
//                             Authentication authentication,
//                             RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "book_update";
//        }
//
//        String userEmail = authentication.getName();
//        bookService.updateBookByUser(bookDto, userEmail);
//
//        redirectAttributes.addFlashAttribute("successUpdateMessage", "Book updated successfully!");
//
//        return "redirect:/user/book/offered";
//    }


}
