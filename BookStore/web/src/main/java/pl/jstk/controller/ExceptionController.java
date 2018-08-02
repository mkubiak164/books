package pl.jstk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.jstk.constants.ViewNames;
import pl.jstk.exceptions.BookNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleConflict() {
        return ViewNames.FORBIDDEN;
        // Nothing to do

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFoundConflict() {
        return ViewNames.BOOK_NOT_FOUND;
        // Nothing to do

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(NoHandlerFoundException ex) {
        return ViewNames.NOT_FOUND;
    }

}
