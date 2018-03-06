public class UserRequest {
    Patron patron;
    Document document;

    public UserRequest(Patron patron, Document document){
        this.patron = patron;
        this.document = document;
    }
}
