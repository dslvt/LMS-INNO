import java.time.Period;
import java.util.ArrayList;

public class AVmaterial extends Document {

    public AVmaterial(){

    }

    public AVmaterial(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference){
        this.name = name;
        this.authors = authors;
        this.price = cost;
        this.keywords = keywords;
        this.isReference = isReference;

        type = DocumentType.avmaterial;
    }
}
