import java.time.Period;
import java.util.ArrayList;

public abstract class Document {
    public int id;
    public String name;
    public int price;
    public ArrayList<String> keywords;
    public ArrayList<String> authors;
    public DocumentType type;
    public Period checkOutTime;
    public boolean isReference;
}
