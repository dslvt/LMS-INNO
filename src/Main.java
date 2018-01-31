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
//        authors.add("Pup");
//        ArrayList<String> keywords = new ArrayList<String>();
//        keywords.add("Music"); keywords.add("Rock");
//        AVmaterial mat = new AVmaterial("Sleep In The Heat", authors, 100, keywords, false);
//        mat.CreateDocumentInDB();
//
//        //db.CreateAVMaterial(mat);

//        Booking booking = new Booking();

        try{
            ArrayList<Book> books = db.getAllBooks();
            System.out.println(books.get(0).name + " " + books.get(0).authors.toString());
        }catch (Exception e){
            System.out.println(e.toString() + " main file");
        }
    }

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
