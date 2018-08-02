package pl.jstk.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jstk.constants.ViewNames;
import pl.jstk.exceptions.BookNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public String handleConflict() {
        return ViewNames.ERROR;
        // Nothing to do

    }


}
