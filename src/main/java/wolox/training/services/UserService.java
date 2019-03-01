package wolox.training.services;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import wolox.training.models.Book;
import wolox.training.models.User;

@Service
public interface UserService {
    public abstract User addBook(Long userId, Book bookToAdd) throws JSONException;
    public abstract User addBookById(User user, Long bookId);
}
