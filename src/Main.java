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
//        authors.add("Daryl Hall"); authors.add("John Oates");
//        ArrayList<String> keywords = new ArrayList<String>();
//        keywords.add("Music"); keywords.add("80e");
//        AVmaterial mat = new AVmaterial("You Make My Dreams", authors, 10, keywords, false);
//
//        //db.CreateAVMaterial(mat);

//        Booking booking = new Booking();
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
