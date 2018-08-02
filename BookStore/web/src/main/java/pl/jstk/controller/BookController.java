package pl.jstk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.entity.BookEntity;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.exceptions.BookNotFoundException;
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
	public String getBooksById(@RequestParam("id") Long id, Model model) throws BookNotFoundException  {
	    BookEntity foundBook = bookRepository.getOne(id);
        if(foundBook != null) {
			BookTo book = bookMapper.map(foundBook);
			model.addAttribute(ModelConstants.BOOK, book);
		}
		else {
			throw new BookNotFoundException(id);
		}
        return ViewNames.BOOK;
    }

	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String addBook(@RequestParam("title") String title, @RequestParam("authors") String authors, @RequestParam("status") BookStatus status) {
		BookTo bookTo = new BookTo(title, authors, status);
		BookEntity bookEntity = bookMapper.map(bookTo);

		bookRepository.save(bookEntity);
		return ViewNames.BOOK_ADDED;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String bookAdd(Model model) {
		model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
		return ViewNames.ADD_BOOKS;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/{id}/removeBook", method = RequestMethod.POST)
	public String removeBook(@PathVariable("id") Long id) throws BookNotFoundException {

		Optional<BookEntity> bookEntity = bookRepository.findById(id);
		if (bookEntity.isPresent()) {
			bookRepository.delete(bookEntity.get());
		} else {
			throw new BookNotFoundException(id);
		}
		return ViewNames.BOOK_REMOVED;
	}


}

