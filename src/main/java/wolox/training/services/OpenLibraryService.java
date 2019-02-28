package wolox.training.services;

import org.json.JSONException;
import org.springframework.stereotype.Service;
import wolox.training.models.Book;

@Service
public interface OpenLibraryService {
    public abstract Book findByIsbn(String isbn) throws JSONException;
}
