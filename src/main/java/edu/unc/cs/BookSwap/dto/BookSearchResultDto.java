package edu.unc.cs.BookSwap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResultDto {
    private String bookTitle;
    private String bookAuthor;
    private String ownerEmail;
}
