import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Database db;
    private static Scanner sc;
    private static User user;

    public static void main(String[] args) {
        db = new Database();
        sc = new Scanner(System.in);

        FirstWindow window = new FirstWindow();
        window.setVisible(true);
//        ArrayList<String> author = new ArrayList<>();
//        author.add("author");
//        Book book = new Book("Noname", author, 100, author, false, "publisher","editor",2008, false, "location", true);
//        book.CreateDocumentInDB(5);
//        book.addCopies(2, 5);
//        book.localId = 31;
//        book.deleteCopies(1,5);
//        book.DeleteFromDB(true, 5);
//        book.ModifyInDB("Extra", author, 100, author, false, "publisher","editor",2008, false, "newLocation", 5);


//
//
//        Tester tester = new Tester();
//
//        tester.test1();
//        tester.test2();
//        tester.test3();
//        tester.test4();
//        tester.test5();
//        tester.test6();
//        tester.test7();
//        tester.test8();
//        tester.test9();
//        tester.test10();
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
