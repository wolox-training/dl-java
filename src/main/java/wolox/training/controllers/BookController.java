package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Book> findById(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book newBook){
        return bookRepository.save(newBook);
    }

    @PutMapping("/{id}")
    public Book update(@RequestBody Book book, @PathVariable Long id){
        if(book.getId() == id && bookRepository.findById(id) != null) {
            return bookRepository.save(book);
        }

        return null;  // TODO: error handling
    }

    @DeleteMapping("/{id}")
    public Book delete(@PathVariable Long id){
        if(bookRepository.findById(id) != null){
            bookRepository.deleteById(id);
        }
        return null; // TODO: error handling
    }
}