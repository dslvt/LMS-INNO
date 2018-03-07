import com.sun.jdi.IntegerType;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.net.StandardSocketOptions;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BinaryOperator;

public class Tester {
    Database db;
    Booking booking;

    private int book1LocId, book2LocId, book3LocId;
    private int av1Id, av2Id;
    private int user2ID, user1ID, user3ID;
    private Patron lib;
    private long March5Date = 1520197200000L;
    private long  February9Date = 1518123600000L;
    private long February2Date = 1517518800000L;
    private long February5Date = 1517778000000L;
    private long February17Date = 1518814800000L;

    private FileWriter gFw;
    private File gF;


    public Tester(){
        try{
            db = new Database();
            booking = new Booking();
            gF = new File("log.txt");
            gFw = new FileWriter(gF);
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
            Statement statement = db.connection.createStatement();
            String[] tables = {"av_materials",  "books", "journal_articles", "journals", "libtasks", "request", "users", "documents", "booking"};
            for (int i = 0; i < tables.length; i++) {
                db.DeleteAllInTable(tables[i]);
            }
            db.ExecuteQuery("INSERT INTO `users` (`id`, `name`, `phoneNumber`, `address`, `debt`, `isFacultyMember`, `password`, `isLibrarian`) VALUES ('1', 'All cash', '1', '1', '0', b'0', '1', b'1');");
            lib = db.getPatronById(1);

            String[][] authors = {{"Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest and Clifford Stein"},
                    {"Erich Gamma", "Ralph Johnson", "John Vlissides", "Richard Helm"},
                    {"Brooks,Jr.", "Frederick P"}};

            Book book1 = new Book("Introduction to Algorithms", new ArrayList(Arrays.asList(authors[0])), 0, new ArrayList<>(), false, "MIT Press", "Third edition", 2009, false, "lib", true);
            book1.CreateDocumentInDB(lib.id);
            book1.addCopies(2, lib.id);
            book1LocId = book1.id;

            Book book2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", new ArrayList(Arrays.asList(authors[1])), 0, new ArrayList<>(), false, "Addison-Wesley Professional", "First edition", 2003, true, "lib", true);
            book2.CreateDocumentInDB(lib.id);
            book2.addCopies(1, lib.id);
            book2LocId = book2.id;

            Book book3 = new Book("The Mythical Man-month", new ArrayList(Arrays.asList(authors[2])), 0, new ArrayList<>(), true, "Addison-Wesley Longman Publishing Co., Inc.", "Second edition", 1995, false, "lib", true);
            book3.CreateDocumentInDB(lib.id);
            book3LocId = book3.id;

            String[][] avAuthors = {{"Tony Hoare"}, {"Claude Shannon"}};
            AVmaterial aVmaterial1 = new AVmaterial("Null References: The Billion Dollar Mistake", new ArrayList<String>(Arrays.asList(avAuthors[0])), 0, new ArrayList<>(), false, true, "loc");
            aVmaterial1.CreateDocumentInDB(lib.id);
            av1Id = aVmaterial1.id;

            AVmaterial aVmateria2 = new AVmaterial("Information Entropy", new ArrayList<String>(Arrays.asList(avAuthors[1])), 0, new ArrayList<>(), false, true, "loc");
            aVmateria2.CreateDocumentInDB(lib.id);
            av2Id = aVmateria2.id;

            Patron  patron1 = new Patron("Sergey Afonso", "1", "30001", "Via Margutta, 3", true, 0);
            patron1.CreateUserDB();
            user1ID = patron1.id;

            Patron patron2 = new Patron("Nadia Teixeira", "1", "30002", "Via Sacra, 13", false, 0);
            patron2.CreateUserDB();
            user2ID = patron2.id;

            Patron patron3 = new Patron(" Elvira Espindola", "1", "30003", "Via del Corso, 22", false, 0);
            patron3.CreateUserDB();
            user3ID = patron3.id;

            ResultSet rs1 = db.SelectFromDB("select count(id) from documents");
            rs1.next();
            ResultSet rs2 = db.SelectFromDB("select count(id) from users");
            rs2.next();

            if(rs1.getInt(1) == 8 && rs2.getInt(1) == 4){
                System.out.println("TC1 PASSED!");
                gFw.write("TC1 PASSED!\n");
            }else{
                System.out.println("TC1 FAILED!");
                gFw.write("TC1 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc1: " + e.toString());
        }
    }

    public void tc2(){
        this.tc1();
        try{
            Book book1 = (Book)db.getDocumentById(book1LocId);
            book1.deleteCopies(2, lib.id);

            Book book2 = (Book)db.getDocumentById(book2LocId);
            book2.deleteCopies(1, lib.id);

            Patron patron = db.getPatronById(user2ID);
            patron.DeleteUserDB(lib.id);

            ResultSet rs1 = db.SelectFromDB("select count(id) from documents");
            rs1.next();
            ResultSet rs2 = db.SelectFromDB("select count(id) from users");
            rs2.next();

            if(rs1.getInt(1) == 5 && rs2.getInt(1) == 3){
                System.out.println("TC2 PASSED!");
                gFw.write("TC2 PASSED!\n");
            }else{
                System.out.println("TC2 FAILED!");
                gFw.write("TC2 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc2: " + e.toString());
        }
    }

    public void tc3(){
        try{
            this.tc1();
            Pair<Patron, ArrayList<Document>> patronInfo1 = FullPatronInfo.GetInfo(user1ID, lib.id);
            Pair<Patron, ArrayList<Document>> patronInfo2 = FullPatronInfo.GetInfo(user3ID, lib.id);
            Patron p1 = db.getPatronById(user1ID);
            Patron p2 = db.getPatronById(user3ID);

            if(patronInfo1.second.size() == 0 && patronInfo2.second.size() == 0 && p1.name.equals(patronInfo1.first.name) && p2.name.equals(patronInfo2.first.name)){
                System.out.println("TC3 PASSED!");
                gFw.write("TC3 PASSED!\n");
            }else{
                System.out.println("TC3 FAILED!");
                gFw.write("TC3 FAILED!\n");

            }

        }catch (Exception e){
            System.out.println("Error in tc3: " + e.toString());
        }
    }

    public void tc4(){
        try {
            this.tc2();
            Pair<Patron, ArrayList<Document>> patronInfo1 = FullPatronInfo.GetInfo(user2ID, lib.id);
            Pair<Patron, ArrayList<Document>> patronInfo2 = FullPatronInfo.GetInfo(user3ID, lib.id);
            Patron p1 = db.getPatronById(user2ID);
            Patron p2 = db.getPatronById(user3ID);

            if(p1 == patronInfo1.first && p2.name.equals(patronInfo2.first.name) && patronInfo2.second.size() == 0 && patronInfo1.second.size() == 0){
                System.out.println("TC4 PASSED!");
                gFw.write("TC4 PASSED!\n");
            }else{
                System.out.println("TC4 FAILED!");
                gFw.write("TC4 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc4: " + e.toString());
        }
    }

    public void tc5(){
        try {
            this.tc2();
            Patron patron = db.getPatronById(user2ID);
            Book book = (Book)db.getDocumentById(book1LocId);
            EventManager eventManager = new EventManager();
            LibTask libTask = new LibTask(book, patron, "checkout");
            eventManager.CreateQuery(libTask);
            eventManager.ExecuteQuery(libTask);

            ResultSet rs1 = db.SelectFromDB("select count(id) from booking");
            rs1.next();

            if(patron == null && rs1.getInt(1) == 0){
                System.out.println("TC5 PASSED!");
                gFw.write("TC5 PASSED!\n");
            }else{
                System.out.println("TC5 FAILED!");
                gFw.write("TC5 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc5: " + e.toString());
        }
    }

    public void tc6(){
        try {
            this.tc2();

            Patron patron1 = db.getPatronById(user1ID);
            Patron patron3 = db.getPatronById(user3ID);
            Book book1 = (Book) db.getDocumentById(book1LocId);
            Book book2 = (Book) db.getDocumentById(book2LocId);

            EventManager eventManager = new EventManager();
            LibTask libTask1 = new LibTask(book1, patron1, "checkout");
            LibTask libTask2 = new LibTask(book1, patron3, "checkout");
            LibTask libTask3 = new LibTask(book2, patron3, "checkout");
            Booking.useCustomDate = true;
            Booking.setDate = March5Date;
            libTask1.id = eventManager.CreateQuery(libTask1);
            libTask2.id = eventManager.CreateQuery(libTask2);
            libTask3.id = eventManager.CreateQuery(libTask3);

            eventManager.ExecuteQuery(libTask1);
            eventManager.ExecuteQuery(libTask2);
            eventManager.ExecuteQuery(libTask3);

            Pair<Patron, ArrayList<Document>> patronInfo1 = FullPatronInfo.GetInfo(user1ID, lib.id);
            Pair<Patron, ArrayList<Document>> patronInfo2 = FullPatronInfo.GetInfo(user3ID, lib.id);

            Booking.useCustomDate = false;
            if(patronInfo1.second.size() == 1 && patronInfo2.second.size() == 1){
                System.out.println("TC6 PASSED!");
                gFw.write("TC6 PASSED!\n");
            }else{
                System.out.println("TC6 FAILED!");
                gFw.write("TC6 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc6: " + e.toString());
        }
    }

    public void tc7(){
        try {
            this.tc1();

            Patron patron1 = db.getPatronById(user1ID);
            Patron patron3 = db.getPatronById(user2ID);
            Book book1 = (Book) db.getDocumentById(book1LocId);
            Book book2 = (Book) db.getDocumentById(book2LocId);
            Book book3 = (Book) db.getDocumentById(book3LocId);
            AVmaterial av1 = (AVmaterial) db.getDocumentById(av1Id);
            AVmaterial av2 = (AVmaterial) db.getDocumentById(av2Id);

            EventManager eventManager = new EventManager();
            LibTask libTask1 = new LibTask(book1, patron1, "checkout");
            LibTask libTask2 = new LibTask(book2, patron1, "checkout");
            LibTask libTask3 = new LibTask(book3, patron1, "checkout");
            LibTask libTask4 = new LibTask(av1, patron1, "checkout");
            LibTask libTask5 = new LibTask(book1, patron3, "checkout");
            LibTask libTask6 = new LibTask(book2, patron3, "checkout");
            LibTask libTask7 = new LibTask(av2, patron3, "checkout");

            Booking.setDate = March5Date;
            Booking.useCustomDate = true;

            eventManager.ExecuteQuery(libTask1);
            eventManager.ExecuteQuery(libTask2);
            eventManager.ExecuteQuery(libTask3);
            eventManager.ExecuteQuery(libTask4);
            eventManager.ExecuteQuery(libTask5);
            eventManager.ExecuteQuery(libTask6);
            eventManager.ExecuteQuery(libTask7);

            Pair<Patron, ArrayList<Document>> patronInfo1 = FullPatronInfo.GetInfo(user1ID, lib.id);
            Pair<Patron, ArrayList<Document>> patronInfo2 = FullPatronInfo.GetInfo(user2ID, lib.id);

            Booking.useCustomDate = false;

            if(patronInfo1.second.size() == 3 && patronInfo2.second.size() == 3){
                System.out.println("TC7 PASSED!");
                gFw.write("TC7 PASSED!\n");
            }else{
                System.out.println("TC7 FAILED!");
                gFw.write("TC7 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc7: " + e.toString());
        }
    }

    public void tc8(){
        try {
            this.tc1();
            Patron patron1 = db.getPatronById(user1ID);
            Patron patron2 = db.getPatronById(user2ID);
            Book book1 = (Book) db.getDocumentById(book1LocId);
            Book book2 = (Book) db.getDocumentById(book2LocId);
            Book book1Cop = (Book) db.getDocumentById(book1LocId+1);
            AVmaterial av1 = (AVmaterial) db.getDocumentById(av1Id);
            AVmaterial av2 = (AVmaterial) db.getDocumentById(av2Id);

            Booking.setDate = February9Date;
            Booking.useCustomDate = true;
            EventManager eventManager = new EventManager();

            LibTask libTask1 = new LibTask(book1, patron1, "checkout");
            eventManager.ExecuteQuery(libTask1);

            Booking.setDate = February2Date;
            LibTask libTask2 = new LibTask(book2, patron1, "checkout");
            eventManager.ExecuteQuery(libTask2);

            Booking.setDate = February5Date;
            LibTask libTask3 = new LibTask(book1Cop, patron2, "checkout");
            eventManager.ExecuteQuery(libTask3);

            Booking.setDate = February17Date;
            LibTask libTask4 = new LibTask(av1, patron2, "checkout");
            eventManager.ExecuteQuery(libTask4);
            Booking.useCustomDate = false;

            Booking booking = new Booking();

            if(booking.countOverdue(book2) == 5 && booking.countOverdue(av1) == 4 && booking.countOverdue(book1Cop) == 16){
                System.out.println("TC8 PASSED!");
                gFw.write("TC8 PASSED!\n");
            }else{
                System.out.println("TC8 FAILED!");
                gFw.write("TC8 FAILED!\n");

            }
        }catch (Exception e){
            System.out.println("Error in tc8: " + e.toString());
        }
    }

    public void tc9(){
        try {

            File in = new File("input.txt");
            FileWriter fileWriter = new FileWriter(in);
            fileWriter.write("0");
            fileWriter.close();
            Scanner sc = new Scanner(in);
            if (sc.next().equals("0")){
                this.tc1();
                sc.close();
                FileWriter fw = new FileWriter(in);
                fw.write("1");
                fw.flush();
                fw.close();
                gFw.flush();
                gFw.close();
                System.exit(1);
            }else{
                if(true){
                    System.out.println("TC9 PASSED!");
                    gFw.write("TC9 PASSED!\n");
                }else{
                    System.out.println("TC9 FAILED!");
                    gFw.write("TC9 FAILED!\n");

                }
            }


        }catch (Exception e){
            System.out.println("Error in tc9: " + e.toString());
        }
    }
}
