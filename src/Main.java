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
//        authors.add("Alish");
//        ArrayList<String> keywords = new ArrayList<String>();
//        keywords.add("Poem"); keywords.add("Tatar");
//        Book mat = new Book("Alish Shigirler", authors, 10, keywords, false, "pub", "first", 2016, false);
//        mat.CreateDocumentInDB();

        //db.CreateAVMaterial(mat);

        //Booking booking = new Booking();

        FirstWindow window = new FirstWindow();
        window.setVisible(true);


        Tester tester = new Tester();

        tester.test1();
        tester.test2();
        tester.test3();
        tester.test4();
        tester.test5();
        tester.test6();
        tester.test7();
        tester.test8();
        tester.test9();
        tester.test10();
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
