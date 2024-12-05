package edu.unc.cs.BookSwap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Column( name="title", nullable = false)
    private String bookTitle;

    @Column( name="author")
    private String bookAuthor;

    public Book(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }
}

//    @Column (name = "genre")
//    private String bookGenre;
//
//    @Column (name = "isbn")
//    private String bookISBN;
//
//    @Column (name = "year_of_publication")
//    private String publicationYear;
//
//    @Column (name = "edition")
//    private String bookEdition;
