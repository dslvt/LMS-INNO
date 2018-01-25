import java.util.ArrayList;

public class Article extends Document {
    public Journal journal;

    public Article(String name, ArrayList<String> authors, Journal journal){
        this.name = name;
        this.authors = authors;
        this.journal = journal;

        type = DocumentType.article;
    }
}
