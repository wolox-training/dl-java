package wolox.training.models;

import com.google.common.base.Preconditions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;


@Entity
public class Book {

    public Book() {}

    public Book(String externalJson, String isbn) throws JSONException {
        JSONObject bookJson = new JSONObject(externalJson);
        JSONArray authorJson = bookJson.getJSONArray("authors");
        JSONArray publisherJson = bookJson.getJSONArray("publishers");
        JSONArray genresJson =  bookJson.has("subjects") ? bookJson.getJSONArray("subjects"): null;
        this.isbn = isbn;
        this.title = bookJson.getString("title");
        this.subtitle = bookJson.has("subtitle") ? bookJson.getString("subtitle"): "";
        this.year =  bookJson.has("publish_date")? bookJson.getString("publish_date"): "";
        this.pages = Integer.parseInt(bookJson.getString("number_of_pages"));
        this.author = authorJson.getJSONObject(0).getString("name");
        this.publisher = publisherJson.getJSONObject(0).getString("name");
        this.image = bookJson.getJSONObject("cover").getString("medium");
        this.genre = genresJson == null ? null: genresJson.getJSONObject(0).getString("name");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;


    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkArgument( author != null && !author.isEmpty());
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkArgument( image != null && !image.isEmpty());
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkArgument( title != null && !title.isEmpty());
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkArgument( subtitle != null && !subtitle.isEmpty());
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkArgument( publisher != null && !publisher.isEmpty());
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkArgument( year != null && !year.isEmpty());
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = Preconditions.checkNotNull(pages);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkArgument( isbn != null && !isbn.isEmpty());
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o){
        if(o == null)   return false;
        if(!(o instanceof Book)) return false;

        Book other = (Book) o;
        return this.id == other.getId();
    }

    @Override
    public int hashCode() {
       return (int) this.id;
    }
}
