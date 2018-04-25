public class UserRequest {
    Patron patron;
    Document document;

    /**
     * Used to create UserRequest
     */

    public UserRequest(Patron patron, Document document){
        this.patron = patron;
        this.document = document;
    }
}
