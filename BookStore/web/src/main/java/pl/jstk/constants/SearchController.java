package pl.jstk.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.entity.BookEntity;
import pl.jstk.exceptions.BookNotFoundException;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.to.BookTo;

import java.util.*;

@RequestMapping //("search")
public class SearchController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    Map<String, BookTo> bookData = new HashMap<String, BookTo>();

    @RequestMapping(value = "findBook", method = RequestMethod.POST)
    public String findByAuthorAndTitle(@RequestParam("author") String author, @RequestParam("title") String title){

        List<BookEntity> bookAuthorEntity = bookRepository.findBookByAuthorAndTitle(author, title);
        List<BookTo> bookTos = bookMapper.map2To(bookAuthorEntity);
       // model.addAttribute(ModelConstants.BOOK_LIST, bookTos);
        return ViewNames.SEARCH;
    }

}









