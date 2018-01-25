import java.util.ArrayList;

public class Book extends Document{

    public String publisher, edition, publishYear;

    public Book (String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publisher, String edition, String publishYear){
        this.name = name;
        this.authors = authors;
        this.price = cost;
        this.keywords = keywords;
        this.isReference = isReference;
        this.publisher = publisher;
        this.edition = edition;
        this.publishYear = publishYear;

        type = DocumentType.book;
    }


}
