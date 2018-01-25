import java.util.ArrayList;

public class Journal extends Document {

    public String publicationDate;

    public Journal(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publicationDate){
        this.name = name;
        this.authors = authors;
        this.price = cost;
        this.keywords = keywords;
        this.isReference = isReference;
        this.publicationDate = publicationDate;

        type = DocumentType.journal;
    }
}
