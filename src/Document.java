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

    /**
     * Create document in database
     * @param idLibrarian Id of librarian to check his privileges
     */

    public void CreateDocumentInDB(int idLibrarian) {
    }

    /**
     * Delete document from database
     * @param idLibrarian Id of librarian to check his privileges
     */

    public void DeleteFromDB(int idLibrarian) {
    }

    /**
     * Check possibility to take document
     */

    public abstract boolean isCanBeTaken();

    /**
     * Create copies of current document
     * @param copies Number of copies which are needed to create
     * @param idLibrarian Id of librarian to check his privileges
     */

    public abstract ArrayList<Document> addCopies(int copies, int idLibrarian);

    /**
     * Delete copies of current document
     * @param copies Number of copies which are needed to delete
     * @param idLibrarian Id of librarian to check his privileges
     */

    public abstract void deleteCopies(int copies, int idLibrarian);

    /**
     * Return string with type of document
     * @param dt DocumentType from enumeration
     * @return String with type
     */

    public static String getParsedType(DocumentType dt){
        String ans = "";
        switch (dt){
            case book:
                ans = "books";
                break;
            case journal:
                ans = "journals";
                break;
            case av_material:
                ans = "av_materials";
                break;
            case article:
                ans = "journal_articles";
                break;
        }
        return ans;
    }

    /**
     * Return unique key which consist of type and local id
     * @param doc Document which unique key is needed
     */

    public static String getUnicKey(Document doc){
        String ans = "";
        ans = getParsedType(doc.type) + " " + Integer.toString(doc.localId);
        return ans;
    }
}
