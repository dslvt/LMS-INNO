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
    public int localId;

    /**
     * common feature
     */

    public void CreateDocument(String name, int price, ArrayList<String> keywords, ArrayList<String> authors, boolean isReference, boolean isActive, String location) {
        this.name = name;
        this.price = price;
        this.keywords = keywords;
        this.authors = authors;
        this.isReference = isReference;
        this.isActive = isActive;
        this.location = location;
    }

    public void CreateDocumentInDB(int idLibrarian) {
    }

    public void DeleteFromDB(int idLibrarian) {
    }

    public abstract boolean isCanBeTaken();

    public abstract ArrayList<Document> addCopies(int copies, int idLibrarian);

    public abstract void deleteCopies(int copies, int idLibrarian);
}
