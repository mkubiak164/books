package pl.jstk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jstk.constants.ViewNames;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Book no found")
public class BookNotFoundException extends Exception {

    private static final long serialVersionUID = -3332292346834265371L;

    public BookNotFoundException(long id){
        super("BookNotFoundException with id="+id);
    }

}
