package pl.jstk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.jstk.constants.ViewNames;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private static final String ADDED = "Book was added";

    private List<BookTo> setBooks(int size, BookStatus bookStatus) {
        List<BookTo> books = new ArrayList<>();
        BookTo bookTo = new BookTo();


        bookTo.setStatus(bookStatus);
        bookTo.setTitle("T0");
        bookTo.setAuthors("Autor0");
        books.add(bookTo);
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                bookTo = new BookTo();
                bookTo.setStatus(bookStatus);
                bookTo.setTitle("T" + i);
                bookTo.setAuthors("Autor" + i);
                books.add(bookTo);
            }
        }
        return books;
    }

    @Test
    public void testBooksPage() throws Exception {
        //given
        List<BookTo> books = setBooks(4, BookStatus.FREE);

        // when
        when(bookService.findAllBooks()).thenReturn(books);
        ResultActions resultActions = mockMvc.perform(get("/books"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOKS))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(model().attribute("bookList", books));

    }

    @Test
    public void shouldReturnBookWithId1() throws Exception {
        //given
        BookTo bookTo = new BookTo(1L, "t", "a", BookStatus.FREE);

        // when
        when(bookService.findBookById(Mockito.anyLong())).thenReturn(bookTo);
        ResultActions resultActions = mockMvc.perform(get("/books/book?id=1"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.BOOK))
                .andDo(print())
                .andExpect(content().string(containsString("")))
                .andExpect(model().attribute("book", bookTo));

    }


    @Test
    public void schouldReturnNewBookView() throws Exception {
        //given when
        ResultActions resultActions = mockMvc.perform(get("/books/add"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.NEW_BOOK))
                .andDo(print())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void schouldReturnWelcomeViewAfterAdd() throws Exception {
        //given when
        BookTo bookTo = new BookTo(null, "title", "author", BookStatus.FREE);
        String asJsonString = new ObjectMapper().writeValueAsString(bookTo);

        //when
        ResultActions resultActions = mockMvc.perform(post("/greeting").contentType(MediaType.APPLICATION_JSON).content(asJsonString));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.WELCOME))
                .andDo(print())
                .andExpect(model().attribute("info", ADDED));
        verify(bookService, times(1)).saveBook(Mockito.any(BookTo.class));

    }

    @Test
    public void schouldReturnResourceNotFoundException() throws Exception {
        //given when
        BookTo bookTo = null;

        //when
        when(bookService.findBookById(20)).thenReturn(bookTo);
        ResultActions resultActions = mockMvc.perform(get("/books/book?id=20"));

        // then
        resultActions.andExpect(status().is4xxClientError());


        verify(bookService, times(1)).findBookById(20);

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void shouldReturnBookViewAfterDelete() throws Exception {
        List<BookTo> books = setBooks(4, BookStatus.FREE);

        //when
        ResultActions resultActions = mockMvc.perform(post("/books/delete?id=1"));

        // then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/" + ViewNames.BOOKS));

        verify(bookService, times(1)).deleteBook(Mockito.anyLong());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldReturn403() throws Exception {
        List<BookTo> books = setBooks(4, BookStatus.FREE);

        //when
        ResultActions resultActions = mockMvc.perform(post("/books/delete?id=1"));

        // then
        resultActions.andExpect(status().is4xxClientError());

        verify(bookService, times(0)).deleteBook(Mockito.anyLong());
    }
}
