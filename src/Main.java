import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Database db;
    private static Scanner sc;
    private static User user;

    public static void main(String[] args) {
        db = new Database();
        sc = new Scanner(System.in);

//        Authorization();
//
//        ArrayList<String> authors = new ArrayList<String>();
//        authors.add("Dan Jones");
//        ArrayList<String> keywords = new ArrayList<String>();
//        keywords.add("History"); keywords.add("Secretive");
//        Book mat = new Book(1, "The Templars", authors, 10, keywords, false, "amazon", "first", 2017, true);
//        mat.CreateDocumentInDB();
//
//        //db.CreateAVMaterial(mat);

//        Booking booking = new Booking();

        FirstWindow window = new FirstWindow();
        window.setVisible(true);

        ArrayList<Document> documents = db.getUserDocuments(db.getPatronByNumber("1"));

        for (int i = 0; i < documents.size(); i++) {
            System.out.println(documents.get(i).name);
        }
    }

    /**
     * testing feature
     */
    private static void Authorization(){
        boolean isAuthorizated = false;

        while (!isAuthorizated){
            String tUsername = sc.nextLine();
            String tPass = sc.nextLine();

            if(db.isCorrectAuthorization(tUsername, tPass)){
                isAuthorizated = true;
                System.out.println("Correct authorization");
                //user = db.GetUserByLogin(tUsername);
            }else{
                System.out.println("Incorrect login/pass");
            }
        }
    }
}
