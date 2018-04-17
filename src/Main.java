import javax.xml.crypto.Data;
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

        boolean isTesting = false;
        Database.DeleteAllFromDBAndCreateAdmin();

        /**
         * if want test program set isTesting true
         */
        if(isTesting) {
            Tester tester = new Tester();
//        tester.tc1();
//        tester.tc2();
//        tester.tc3();
//        tester.tc4();
//        tester.tc5();
//        tester.tc6();
//        tester.tc7();
//        tester.tc8();
//        tester.tc9();
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

//        tester.init3();
//        tester.findUsersAndBooks();
//        tester.clearHistory();
            tester.t1();
            tester.t2();
            tester.t3();
//            tester.t4();
            tester.t5();
            tester.t6();
            tester.t7();
            tester.t8();
            tester.t9();
            tester.t10();
//            CurrentSession.setDate = 0L;
//            FirstWindow window = new FirstWindow();
//            window.setVisible(true);
        }else{
            CurrentSession.setDate = 0L;
            FirstWindow window = new FirstWindow();
            window.setVisible(true);
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
