import java.util.ArrayList;
import java.sql.SQLException;

public class Article extends Document {
    public Journal journal;

    public Article(String name, ArrayList<String> authors, Journal journal){
        this.name = name;
        this.authors = authors;
        this.journal = journal;

        type = DocumentType.article;
    }

    @Override
    public void CreateDocumentInDB() throws SQLException{
        super.CreateDocumentInDB();


    }
}
