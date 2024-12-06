package edu.unc.cs.BookSwap.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long bid;

    @NotEmpty(message = "Book title cannot be empty")
    @Size(max = 100, message = "Book title must be less than 100 characters")
    private String bookTitle;

    @NotEmpty(message = "Author name cannot be empty")
    private String bookAuthor;
}
//    private String bookGenre;
//    private String bookISBN;
//    private String publicationYear;
//    private String bookEdition;
