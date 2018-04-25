public class Request {
    Document document;
    String message;

    /**
     * Constructor to create request
     * @param document Document on which request is send
     * @param message Message which is shown to user
     */

    Request(Document document, String message){
        this.document = document;
        this.message = message;
    }

}
