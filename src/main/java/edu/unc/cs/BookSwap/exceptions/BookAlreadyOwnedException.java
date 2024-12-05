package edu.unc.cs.BookSwap.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookAlreadyOwnedException extends RuntimeException{

    public BookAlreadyOwnedException(String message) {
        super(message);
    }

}