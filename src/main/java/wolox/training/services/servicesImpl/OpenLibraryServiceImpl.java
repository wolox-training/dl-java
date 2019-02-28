package wolox.training.services.servicesImpl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.constants.ApiConstants;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@Service
public class OpenLibraryServiceImpl implements OpenLibraryService {

    @Autowired
    BookRepository bookRepository;

    public Book findByIsbn(String isbn) throws JSONException {

        Book localBook = bookRepository.findByIsbn(isbn);
        if(localBook == null) {
            final String params = "?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
            final String uri = ApiConstants.OPEN_LIBRARY_API + params;
            RestTemplate restTemplate =  new RestTemplate();
            JSONObject jsonResponse = new JSONObject(restTemplate.getForObject(uri, String.class));
            if(jsonResponse.length() == 0){
                throw new BookNotFoundException();
            }else {
                Book newBook = new Book(jsonResponse.getString("ISBN:" + isbn), isbn);
                return bookRepository.save(newBook);
            }
        }else {
            return localBook;
        }
    }
}
