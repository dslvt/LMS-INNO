public class LibTask {
    public String type;
    public Document document;
    public Patron user;
    public int id;
    public int queue;
    public String unic_key;

    /**
     * Constructor to create request from users
     */

    public LibTask(Document document, Patron user, String type, boolean isHasUnicKey){
        this.document = document;
        this.user = user;
        this.type = type;
        if(isHasUnicKey)
            unic_key = Document.getUnicKey(document);
        else
            unic_key = "";
    }
}
