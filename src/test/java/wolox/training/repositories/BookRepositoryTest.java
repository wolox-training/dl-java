package wolox.training.repositories;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.constants.TestConstants;
import wolox.training.models.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    private Book testBook;

    @Before
    public void init() throws JSONException {
        testBook = new Book(TestConstants.EXTERNAL_BOOK_JSON, TestConstants.BOOK_ISBN);
        bookRepository.save(testBook);
    }

    @Test
    public void whenFindByAuthor_thenReturnBooks(){
        List<Book> byAuthor = bookRepository.findByAuthor(testBook.getAuthor());
        assertThat(byAuthor).isNotEmpty().containsOnly(testBook);
    }

    @Test
    public void whenFindByIsbn_thenReturnBook(){
        Book book = bookRepository.findByIsbn(testBook.getIsbn());
        assertThat(book).isNotNull().isEqualTo(book);
    }

    @Test
    public void WhenFindByIsbn_thenReturnNull(){
        Book book = bookRepository.findByIsbn("00000000");
        assertThat(book).isNull();
    }

}
