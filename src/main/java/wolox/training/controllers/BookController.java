package wolox.training.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    OpenLibraryService openLibraryService;

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) throws BookNotFoundException {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @GetMapping("/isbn/{isbn}")
    public Book findByIsbn(@PathVariable String isbn) throws JSONException {
       return openLibraryService.findByIsbn(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book newBook){
        return bookRepository.save(newBook);
    }

    @PutMapping("/{id}")
    public Book update(@RequestBody Book book, @PathVariable Long id) throws BookNotFoundException {
        if(book.getId() == id) {
            bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
            return bookRepository.save(book);
        }
        return null;  // TODO: error handling
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws BookNotFoundException {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }
}