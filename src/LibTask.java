public class LibTask {
    public String type;
    public Document document;
    public Patron user;
    public int id;
    public int queue;
    public String unic_key;

    public LibTask(Document document, Patron user, String type){
        this.document = document;
        this.user = user;
        this.type = type;
    }
}
