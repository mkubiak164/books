package pl.jstk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public List<BookTo> getAllBooks() {
		List<BookEntity> allBooks = bookRepository.findAllBooks();
		return bookMapper.map2To(allBooks);
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

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public BookTo getBooksById(@PathParam("id") Long id) {
		BookEntity foundBook = bookRepository.getOne(id);
		return bookMapper.map(foundBook);
	}
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	//public String addBook(@ModelAttribute("newBook") BookTo newBook) {
	public String addBook(@RequestParam("title") String title, @RequestParam("authors") String authors, @RequestParam("status") BookStatus status) {
		BookTo bookTo = new BookTo(title, authors, status);
		BookEntity bookEntity = bookMapper.map(bookTo);
		bookRepository.save(bookEntity);
//		BookEntity addBook = bookRepository.addBook(bookEntity);
		return "You add a book!";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String bookAdd(Model model) {
		model.addAttribute(ModelConstants.NEW_BOOK, new BookTo());
		return ViewNames.ADD_BOOKS;
	}

}

