import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Document {
    public int id;
    public String name;
    public int price;
    public ArrayList<String> keywords;
    public ArrayList<String> authors;
    public DocumentType type;
    public boolean isReference;
    public String location;
    public boolean isActive;

    /**
     * common feature
     */
    public void CreateDocumentInDB(){}

    public abstract boolean isCanBeTaken(Document document);
}
