package wolox.training.services.servicesImpl;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.OpenLibraryService;
import wolox.training.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OpenLibraryService openLibraryService;

    @Autowired
    BookRepository bookRepository;

    public User addBook(Long userId, Book bookToAdd) throws JSONException {
        Optional<User> optional = userRepository.findById(userId);
        Book book = openLibraryService.findByIsbn(bookToAdd.getIsbn());
        if(optional.isPresent() && book != null){ // controller validates that user always exists.
            User user = optional.get();
            if(!user.getBooks().contains(book)){
                user.getBooks().add(book);
                return userRepository.save(user);
            } else {
                throw new BookAlreadyOwnedException();
            }
        } else {
            throw new BookNotFoundException();
        }
    }

    public User addBookById(User user, Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
            user.getBooks().add(book);
            return userRepository.save(user);
    }
}
