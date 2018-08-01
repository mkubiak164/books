package pl.jstk.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.jstk.BookApplication;
import pl.jstk.entity.BookEntity;
import pl.jstk.repository.BookRepository;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, BookApplication.class})
@WebAppConfiguration
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookRepository bookRepository;

    private MockMvc mockMvc;

    @Test
    public void shouldAllBooks() throws Exception {

        ArrayList<BookEntity> books = new ArrayList<BookEntity>();

        Mockito.when(bookRepository.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk());


    }
}