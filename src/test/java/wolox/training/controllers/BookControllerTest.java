package wolox.training.controllers;


import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.constants.TestConstants;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private OpenLibraryService openLibraryService;

    private Book testBook;

    @Before
    public void init() throws JSONException {
        testBook = new Book(TestConstants.EXTERNAL_BOOK_JSON, TestConstants.BOOK_ISBN);
        testBook.setId(1);
    }

    @Test
    public void givenBooks_whenFindById_thenReturnBook() throws Exception {
        given(bookRepository.findById(testBook.getId())).willReturn(Optional.of(testBook));
        mvc.perform(get("/api/books/" + testBook.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((int) testBook.getId())));
    }

    @Test
    public void givenBooks_whenFindByIsbn_thenReturnBook() throws Exception {
        given(openLibraryService.findByIsbn(testBook.getIsbn())).willReturn(testBook);
        mvc.perform(get("/api/books/isbn/" + testBook.getIsbn())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn", is(testBook.getIsbn())));
    }

    @Test
    public void givenBooks_whenCreateBook_thenReturnNewBook() throws Exception {
        given(bookRepository.save(testBook)).willReturn(testBook);
        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestConstants.BOOK_JSON))
                .andExpect(status().isCreated());
    }
}
