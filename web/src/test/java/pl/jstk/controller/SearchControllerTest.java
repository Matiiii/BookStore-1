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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.jstk.constants.ViewNames;
import pl.jstk.enumerations.BookStatus;
import pl.jstk.service.SearchService;
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
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;


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
    public void testSearchPage() throws Exception {
        // given when
        ResultActions resultActions = mockMvc.perform(get("/search"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.SEARCH))
                .andDo(print())
                .andExpect(content().string(containsString("")));

    }

    @Test
    public void shouldReturn4BookByStatusFree() throws Exception {
        // given
        List<BookTo> books = setBooks(4, BookStatus.FREE);
        //books.add(new BookTo(1L, "a", "e", BookStatus.FREE));
        BookTo bookTo = new BookTo(null, null, null, BookStatus.FREE);
        //bookTo.setStatus(BookStatus.FREE);
        String asJsonString = new ObjectMapper().writeValueAsString(bookTo);

        //when
        when(searchService.findAllBooks(Mockito.any(BookTo.class))).thenReturn(books);

        ResultActions resultActions = mockMvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(asJsonString));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.SEARCHED))
                .andDo(print())
                .andExpect(model().attribute("bookList", books));

        verify(searchService, times(1)).findAllBooks(Mockito.any());

    }

    @Test
    public void shouldReturn2BookByAuthorJan() throws Exception {
        // given
        List<BookTo> books = setBooks(2, BookStatus.FREE);
        //books.add(new BookTo(1L, "a", "e", BookStatus.FREE));
        BookTo bookTo = new BookTo(null, null, "Jan", null);
        //bookTo.setStatus(BookStatus.FREE);
        String asJsonString = new ObjectMapper().writeValueAsString(bookTo);

        //when
        when(searchService.findAllBooks(Mockito.any(BookTo.class))).thenReturn(books);

        ResultActions resultActions = mockMvc.perform(post("/search").contentType(MediaType.APPLICATION_JSON).content(asJsonString));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name(ViewNames.SEARCHED))
                .andDo(print())
                .andExpect(model().attribute("bookList", books));

        verify(searchService, times(1)).findAllBooks(Mockito.any());

    }


    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:resources/templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

}
