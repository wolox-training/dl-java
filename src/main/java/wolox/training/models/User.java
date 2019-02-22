package wolox.training.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private Collection<Book> books;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    public boolean addBook(Book newBook) {
        if(this.books.stream().anyMatch(book -> book.getId() == newBook.getId())){
            return false;
        } else {
            return this.books.add(newBook);
        }
    }

    public Collection<Book> deleteBook(long bookToDeleteId){
        if(this.books.stream().anyMatch(book -> book.getId() == bookToDeleteId)){
            this.books.removeIf(book -> book.getId() == bookToDeleteId);
        }
        return this.books;
    }
}
