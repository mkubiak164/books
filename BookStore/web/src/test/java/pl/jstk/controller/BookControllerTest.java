package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.jstk.BookApplication;
import pl.jstk.constants.ModelConstants;
import pl.jstk.entity.BookEntity;
import pl.jstk.repository.BookRepository;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BookApplication.class})
@WebAppConfiguration
@SpringBootTest
public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void shouldAllBooks() throws Exception {

        ArrayList<BookEntity> books = new ArrayList<BookEntity>();

        Mockito.when(bookRepository.findAllBooks()).thenReturn(books);

        ResultActions resultActions = mockMvc.perform(get("/books"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute(ModelConstants.BOOK_LIST, books));

    }
}