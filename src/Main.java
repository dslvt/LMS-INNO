import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Database db;
    private static Scanner sc;
    private static User user;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
//        Librarian librarian = new Librarian();
//        Patron patron = new Patron();
//        patron.id = 6;
//        Booking booking = new Booking();
//        booking.checkOut(book, patron);
//
//        librarian.sendRequest(book,patron);
//        System.out.println(patron.getAllRequests());

//
//
        Tester tester = new Tester();
//        tester.tc1();
//        tester.tc2();
//        tester.tc3();
//        tester.tc4();
//        tester.tc5();
//        tester.tc6();
        tester.tc7();
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
