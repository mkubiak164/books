package pl.jstk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.entity.BookEntity;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.to.BookTo;

import javax.websocket.server.PathParam;


@Controller
@RequestMapping("books")
public class BookController {

	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookMapper bookMapper;
	
	Map<String, BookTo> bookData = new HashMap<String, BookTo>();
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAllBooks(Model model) {
		List<BookEntity> allBooks = bookRepository.findAllBooks();
        List<BookTo> bookTos = bookMapper.map2To(allBooks);
        model.addAttribute(ModelConstants.BOOK_LIST, bookTos);
        return ViewNames.BOOKS;
	}

	@RequestMapping(value = "author", method = RequestMethod.GET)
	public List<BookTo> getBooksByAuthor(@RequestParam String author) {
		List<BookEntity> foundBooks = bookRepository.findBookByAuthor(author);
		return bookMapper.map2To(foundBooks);
	}
	
	@RequestMapping(value = "title", method = RequestMethod.GET)
	public List<BookTo> getBooksByTitle(@RequestParam String title) {
		List<BookEntity> foundBooks = bookRepository.findBookByTitle(title);
		return bookMapper.map2To(foundBooks);
	}

	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public String getBooksById(@RequestParam("id") Long id, Model model) {
	    BookEntity foundBook = bookRepository.getOne(id);
        BookTo book = bookMapper.map(foundBook);
        model.addAttribute(ModelConstants.BOOK, book);
        return ViewNames.BOOK;
    }
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String addBook(@RequestParam("title") String title, @RequestParam("authors") String authors, @RequestParam("status") BookStatus status) {
		BookTo bookTo = new BookTo(title, authors, status);
		BookEntity bookEntity = bookMapper.map(bookTo);

		bookRepository.save(bookEntity);
		return "You add a book!";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String bookAdd(Model model) {
		model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
		return ViewNames.ADD_BOOKS;
	}

	@RequestMapping(value = "/removeBook", method = RequestMethod.POST)
	public String removeBook(@RequestParam ("id") Long id) {
		BookTo bookTo = new BookTo(id);
		BookEntity bookEntity = bookMapper.map(bookTo);
		bookRepository.delete(bookEntity);
		return "You removed a book!";
	}


	/*@RequestMapping(value = "/removeBook", method = RequestMethod.POST)
	public String bookRemove(Model model) {
		model.addAttribute(ModelConstants.CURRENT_BOOK, new BookTo());
		return "You removed a book";
	}*/

}

