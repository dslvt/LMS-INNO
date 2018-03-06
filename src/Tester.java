import javax.xml.crypto.Data;
import java.net.StandardSocketOptions;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class Tester {
    Database db;
    Booking booking;

    public Tester(){
        try{
            db = new Database();
            booking = new Booking();
        }catch(Exception e){
            System.out.println("Error in Tester " + e.toString());
        }

    }

    public void test1(){
        try {
            int id = 19;
            Patron patron = db.getPatronByNumber("1");
            Book book = (Book)db.getDocumentById(id);
            booking.checkOut(book , patron);

            boolean fl = false;
            ArrayList<Document> documents = db.getUserDocuments(patron);
            for (int i = 0; i < documents.size(); i++) {
                if(documents.get(i).localId == book.localId && Database.getAmountOfCurrentBook(book) == 1){
                    fl = true;
                    break;
                }
            }

            if(fl){
                System.out.println("TEST1 PASSED");
            }else{
                System.out.println("TEST1 FAILED");
            }

            booking.returnBook(book, patron);
        }
        catch(Exception e){
            System.out.println("Error in test1 " + e.toString());
        }
    }

    public void test2(){
        try{
            int id = 25;

            Patron patron = db.getPatronByNumber("1");
            Book book = (Book) db.getDocumentById(id);

            int n = booking.checkOut(book, patron);
            if(n == -1){
                System.out.println("TEST2 PASSED");
            }else{
                System.out.println("TEST2 FAILED");
            }
        }
        catch (Exception e){
            System.out.println("Error in test2 " + e.toString());
        }
    }

    public void test3(){
        try{
            int id = 19 ;

            Patron patron = db.getPatronByNumber("777");//facul
            Book book = (Book) db.getDocumentById(id);

            if(booking.checkOut(book , patron) == 28){
                System.out.println("TEST3 PASSED");
            }else{
                System.out.println("TEST3 FAILED");
            }

            booking.returnBook(book, patron);
        }
        catch (Exception e){
            System.out.println("Error in test3 " + e.toString());
        }
    }

    public void test4(){
        try{
            int id = 24;

            Patron patron = db.getPatronByNumber("777");
            Book book = (Book) db.getDocumentById(id);

            if(booking.checkOut(book , patron) == 14){
                System.out.println("TEST4 PASSED");
            }else{
                System.out.println("TEST4 FAILED");
            }

            booking.returnBook(book, patron);
        }
        catch (Exception e){
            System.out.println("Error in test4 " + e.toString());
        }
    }

    public void test5(){
        try{
            int id = 19;
            Patron patron1 = db.getPatronByNumber("1");
            Patron patron2 = db.getPatronByNumber("89991697701");
            Patron patron3 = db.getPatronByNumber("89991697702");
            Book book = (Book) db.getDocumentById(id);

            int n1 = booking.checkOut(book, patron1);
            int n2 = booking.checkOut(book, patron2);
            int n3 = booking.checkOut(book, patron3);

            if(n1 != -1 && n2 != -1 && n3 == -1){
                System.out.println("TEST5 PASSED");
            }else{
                System.out.println("TEST5 FAILED");
            }

            booking.returnBook(book, patron1);
            booking.returnBook(book, patron2);

        }catch (Exception e){
            System.out.println("Error in test5 " + e.toString());
        }
    }

    public void test6(){
        try{
            int id = 19;
            Book book = (Book) db.getDocumentById(id);
            Patron patron = db.getPatronByNumber("1");

            int n1 = booking.checkOut(book, patron);
            int n2 = booking.checkOut(book, patron);

            if(n1 != -1 && n2 == -1){
                System.out.println("TEST6 PASSED");
            }else{
                System.out.println("TEST6 FAILED");
            }

            booking.returnBook(book, patron);

        }catch (Exception e){
            System.out.println("Error in test6 " + e.toString());
        }
    }

    public  void test7(){
        try{
            int id = 19;
            Patron patron1 = db.getPatronByNumber("1");
            Patron patron2 = db.getPatronByNumber("89991697701");

            Book book = (Book)db.getDocumentById(id);

            int n1 = booking.checkOut(book, patron1);
            int n2 = booking.checkOut(book, patron2);

            if(n1 != -1 && n2 != -1){
                System.out.println("TEST7 PASSED");
            }else{
                System.out.println("TEST7 FAILED");
            }

            booking.returnBook(book, patron1);
            booking.returnBook(book, patron2);

        }catch (Exception e){
            System.out.println("Error in test7 " + e.toString());
        }
    }

    public  void test8(){
        try{
            int id = 19;

            Patron patron1 = db.getPatronByNumber("777"); // faculty
            Patron patron2 = db.getPatronByNumber("89991697701"); //student

            Book book = (Book)db.getDocumentById(id);

            int n1 = booking.checkOut(book, patron2);

            if(n1 == 21){
                System.out.println("TEST8 PASSED");
            }else {
                System.out.println("TEST8 FAILED");
            }

            booking.returnBook(book, patron2);
        }catch (Exception e){
            System.out.println("Error in test8 " + e.toString());
        }
    }

    public void test9(){
        try{
            int id = 24;

            Patron patron1 = db.getPatronByNumber("777"); // faculty
            Patron patron2 = db.getPatronByNumber("89991697701"); //student

            Book book = (Book)db.getDocumentById(id);

            int n1 = booking.checkOut(book, patron2);

            if(n1 == 14){
                System.out.println("TEST9 PASSED");
            }else {
                System.out.println("TEST9 FAILED");
            }

            booking.returnBook(book, patron2);
        }catch (Exception e){
            System.out.println("Error in test9 " + e.toString());
        }
    }

    public void test10(){
        try{
            int id1 = 20, id2 = 19;

            Patron patron = db.getPatronByNumber("1");
            Book book1 = (Book) db.getDocumentById(id1); //ref
            Book book2 = (Book) db.getDocumentById(id2); //norm

            int n1 = booking.checkOut(book1, patron);
            int n2 = booking.checkOut(book2, patron);

            if(n1 == -1 && n2 != -1){
                System.out.println("TEST10 PASSED");
            }else {
                System.out.println("TEST10 FAILED");
            }


            booking.returnBook(book2, patron);
        }catch (Exception e){
            System.out.println("Error in test10 " + e.toString());
        }
    }


    public void tc1(){
        Database db = new Database();
        try {
            Statement statement = Database.connection.createStatement();
        }catch (Exception e){
            System.out.println("Error in tc1: " + e.toString());
        }
    }

    public void tc2(){

    }

    public void tc3(){

    }

    public void tc4(){

    }

    public void tc5(){

    }

    public void tc6(){

    }

    public void tc7(){

    }

    public void tc8(){

    }

    public void tc9(){

    }
}
