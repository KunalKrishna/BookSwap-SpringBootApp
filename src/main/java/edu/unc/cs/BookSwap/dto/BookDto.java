package edu.unc.cs.BookSwap.dto;

import jakarta.persistence.Column;
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
    private String bookTitle;
    private String bookAuthor;
//    private String bookGenre;
//    private String bookISBN;
//    private String publicationYear;
//    private String bookEdition;
}
