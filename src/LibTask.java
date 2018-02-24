public class LibTask {
    public String type;
    public Document document;
    public User user;
    public int id;

    public LibTask(Document document, Patron user, String type){
        this.document = document;
        this.user = user;
        this.type = type;
    }
}
