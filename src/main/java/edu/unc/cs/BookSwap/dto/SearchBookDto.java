package edu.unc.cs.BookSwap.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookDto {

    @NotEmpty(message = "Cannot search for Book with empty title")
    private String bookTitle;
}