package wolox.training.models;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Book> books;

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
        Preconditions.checkArgument( username != null && !username.isEmpty());
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = Preconditions.checkNotNull(birthdate);
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public boolean addBook(Book newBook) {
        return this.books.add(newBook);
    }

    public boolean deleteBook(long bookToDeleteId){
        return this.books.removeIf(book -> book.getId() == bookToDeleteId);
    }

}
