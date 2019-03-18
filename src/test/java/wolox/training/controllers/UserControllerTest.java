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

import static org.mockito.BDDMockito.given;

import wolox.training.constants.TestConstants;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.services.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private User testUser = new User();
    private Set<Book> testBooks =  new HashSet<>();
    private Book testBook;

    @Before
    public void init() throws JSONException {
        testBook = new Book(TestConstants.EXTERNAL_BOOK_JSON, TestConstants.BOOK_ISBN);
        testBooks.add(testBook);
        testUser.setId(1);
        testUser.setUsername("Daniela");
        testUser.setBirthdate(LocalDate.of(1992, 1, 28));
    }

    @Test
    public void givenUsers_whenGetUserByUsername_thenReturnUser() throws Exception {
        given(userRepository.findByUsername(testUser.getUsername())).willReturn(Optional.of(testUser));
        mvc.perform(get("/api/user/" + testUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(testUser.getUsername())));
    }

    @Test
    public void givenUsers_whenCreateUser_thenCreateUser() throws Exception {

       given(userRepository.save(testUser)).willReturn(testUser);
       mvc.perform(post("/api/user")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(TestConstants.USER_JSON))
               .andExpect(status().isCreated());
    }

    @Test
    public void giverUsers_whenAddBookToUser_thenReturnUserWithBook() throws Exception {
        given(userRepository.findById(1L)).willReturn(Optional.of(testUser));
        testUser.setBooks(testBooks);
        given(userService.addBookById(testUser, 1L)).willReturn(testUser);
        mvc.perform(put("/api/user/1/books/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestConstants.USER_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books.length()", is(1)));
    }
}
