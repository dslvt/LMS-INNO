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
    Booking booking;

    private int book1LocId, book2LocId, book3LocId;
    private int av1Id, av2Id;
    private int user2ID, user1ID, user3ID, user5ID, user4ID;
    private Patron lib;
    private long March5Date = 1520208000000L;
    private long  February9Date = 1518123600000L;
    private long February2Date = 1517518800000L;
    private long February5Date = 1517778000000L;
    private long February17Date = 1518814800000L;
    private long April2Date = 1522627200000L;
    private long March29Date = 1522281600000L;
    private long March26Date = 1522022400000L;
    private long April16Date = 1523836800000L;

    private ArrayList<Document> booksIds;
    private ArrayList<Patron> patronsIds;

    private FileWriter gFw;
    private File gF;

    private String[] names = {"Sergey Afonso", "Nadia Teixeira" ,"Elvira Espindola", "Andrey Velo", "Veronika Rama"};
    private String[] addresses = {"Via Margutta, 3", "Via Sacra, 13", "Via del Corso, 22", "Avenida Mazatlan 250", "Stret Atocha, 27"};
    private String[] phoneNumbers = {"30001", "30002", "30003", "30004", "30005"};
    private String[][] authors = {{"Thomas H. Cormen", "Charles E. Leiserson", "Ronald L. Rivest and Clifford Stein"},
            {"Erich Gamma", "Ralph Johnson", "John Vlissides", "Richard Helm"},
            {"Tony Hoare"}};

    private Integer[] price = {5000, 1700, 700};
    private String[] publisher = {"MIT Press", "Addison-Wesley Professional"};
    private Integer[] year = {2009, 2003};
    private Boolean[] isBestseller = {false, true};
    private String[] edition = {"Third edition", "First edition"};
    private PatronType[] patronTypes = {PatronType.professor, PatronType.professor, PatronType.professor, PatronType.student, PatronType.visitingProf};
    private String[] title = {"Introduction to Algorithms", "Design Patterns: Elements of Reusable Object-Oriented Software", "Null References: The Billion Dollar Mistake"};

    private Boolean[] isFaculty = {true, true, true, false, false};

    public Tester(){
        try{
            booking = new Booking();
            gF = new File("log.txt");
            gFw = new FileWriter(gF);
            patronsIds = new ArrayList<>();
            booksIds = new ArrayList<>();
        }catch(Exception e){
            System.out.println("Error in Tester " + e.toString());
        }

    }

    public void test1(){
        try {
            int id = 19;
            Patron patron = Database.getPatronByNumber("1");
            Book book = (Book)Database.getDocumentById(id);
            booking.checkOut(book , patron);

            boolean fl = false;
            ArrayList<Document> documents = Database.getUserDocuments(patron);
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

            Patron patron = Database.getPatronByNumber("1");
            Book book = (Book) Database.getDocumentById(id);

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

            Patron patron = Database.getPatronByNumber("777");//facul
            Book book = (Book) Database.getDocumentById(id);

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

            Patron patron = Database.getPatronByNumber("777");
            Book book = (Book) Database.getDocumentById(id);

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
            Patron patron1 = Database.getPatronByNumber("1");
            Patron patron2 = Database.getPatronByNumber("89991697701");
            Patron patron3 = Database.getPatronByNumber("89991697702");
            Book book = (Book) Database.getDocumentById(id);

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
            Book book = (Book) Database.getDocumentById(id);
            Patron patron = Database.getPatronByNumber("1");

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
            Patron patron1 = Database.getPatronByNumber("1");
            Patron patron2 = Database.getPatronByNumber("89991697701");

            Book book = (Book)Database.getDocumentById(id);

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

            Patron patron1 = Database.getPatronByNumber("777"); // faculty
            Patron patron2 = Database.getPatronByNumber("89991697701"); //student

            Book book = (Book)Database.getDocumentById(id);

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

            Patron patron1 = Database.getPatronByNumber("777"); // faculty
            Patron patron2 = Database.getPatronByNumber("89991697701"); //student

            Book book = (Book)Database.getDocumentById(id);

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

            Patron patron = Database.getPatronByNumber("1");
            Book book1 = (Book) Database.getDocumentById(id1); //ref
            Book book2 = (Book) Database.getDocumentById(id2); //norm

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
        try {
            Statement statement = Database.connection.createStatement();
            String[] tables = {"av_materials",  "books", "journal_articles", "journals", "libtasks", "request", "users", "documents", "booking"};
            for (int i = 0; i < tables.length; i++) {
                Database.DeleteAllInTable(tables[i]);
            }
            Database.ExecuteQuery("INSERT INTO `users` (`id`, `name`, `phoneNumber`, `address`, `debt`, `isFacultyMember`, `password`, `isLibrarian`, `type`) VALUES ('1', 'All cash', '1', '1', '0', b'0', '1', b'1', 'lib');");
            lib = Database.getPatronById(1);

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

            Patron  patron1 = new Patron(names[0], "1", phoneNumbers[0], addresses[0], isFaculty[0], 0, PatronType.student, true);
            patron1.CreateUserDB();
            user1ID = patron1.id;

            Patron patron2 = new Patron(names[1], "1", phoneNumbers[1], addresses[1], isFaculty[1], 0, PatronType.student, true);
            patron2.CreateUserDB();
            user2ID = patron2.id;

            Patron patron3 = new Patron(names[2], "1", phoneNumbers[2], addresses[2], isFaculty[2], 0, PatronType.student, true);
            patron3.CreateUserDB();
            user3ID = patron3.id;

            ResultSet rs1 = Database.SelectFromDB("select count(id) from documents");
            rs1.next();
            ResultSet rs2 = Database.SelectFromDB("select count(id) from users");
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
            Book book1 = (Book)Database.getDocumentById(book1LocId);
            book1.deleteCopies(2, lib.id);

            Book book2 = (Book)Database.getDocumentById(book2LocId);
            book2.deleteCopies(1, lib.id);

            Patron patron = Database.getPatronById(user2ID);
            patron.DeleteUserDB(lib.id);

            ResultSet rs1 = Database.SelectFromDB("select count(id) from documents");
            rs1.next();
            ResultSet rs2 = Database.SelectFromDB("select count(id) from users");
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
            Patron p1 = Database.getPatronById(user1ID);
            Patron p2 = Database.getPatronById(user3ID);

            if(patronInfo1.second.size() == 0 && patronInfo2.second.size() == 0 && p1.name.equals(names[0])
                    && p1.phoneNumber.equals(phoneNumbers[0]) && p1.isFacultyMember == isFaculty[0] && p1.address.equals(addresses[0]) &&
                    p2.name.equals(names[2]) && p2.address.equals(addresses[2]) && p2.phoneNumber.equals(phoneNumbers[2]) && p2.isFacultyMember == isFaculty[2]){
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
            Patron p1 = Database.getPatronById(user2ID);
            Patron p2 = Database.getPatronById(user3ID);

            if(p1 == null && patronInfo1.first == null && patronInfo2.second.size() == 0 && patronInfo1.second.size() == 0 &&
                    p2.name.equals(names[2]) && p2.address.equals(addresses[2]) && p2.phoneNumber.equals(phoneNumbers[2]) && p2.isFacultyMember == isFaculty[2]){
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
            Patron patron = Database.getPatronById(user2ID);
            Book book = (Book)Database.getDocumentById(book1LocId);
            EventManager eventManager = new EventManager();
            LibTask libTask = new LibTask(book, patron, "checkout", true);
            eventManager.CreateQuery(libTask);
            eventManager.ExecuteQuery(libTask);

            ResultSet rs1 = Database.SelectFromDB("select count(id) from booking");
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

            Patron p1 = Database.getPatronById(user1ID);
            Patron p3 = Database.getPatronById(user3ID);
            Book book1 = (Book) Database.getDocumentById(book1LocId);
            Book book2 = (Book) Database.getDocumentById(book2LocId);

            EventManager eventManager = new EventManager();
            LibTask libTask1 = new LibTask(book1, p1, "checkout", true);
            LibTask libTask2 = new LibTask(book1, p3, "checkout", true);
            LibTask libTask3 = new LibTask(book2, p3, "checkout", true);
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
            if(patronInfo1.second.size() == 1 && patronInfo2.second.size() == 1 && p1.name.equals(names[0])
                    && p1.phoneNumber.equals(phoneNumbers[0]) && p1.isFacultyMember == isFaculty[0] && p1.address.equals(addresses[0]) &&
                    p3.name.equals(names[2]) && p3.address.equals(addresses[2]) && p3.phoneNumber.equals(phoneNumbers[2]) && p3.isFacultyMember == isFaculty[2] &&
                    Database.getDocumentReturnDate(patronInfo1.second.get(0)).equals("2018-04-02") && Database.getDocumentReturnDate(patronInfo2.second.get(0)).equals("2018-03-19")){
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

            Patron p1 = Database.getPatronById(user1ID);
            Patron p2 = Database.getPatronById(user2ID);
            Book book1 = (Book) Database.getDocumentById(book1LocId);
            Book book2 = (Book) Database.getDocumentById(book2LocId);
            Book book3 = (Book) Database.getDocumentById(book3LocId);
            Book book1Cop = (Book) Database.getDocumentById(book1LocId-1);
            Book book2Cop = (Book) Database.getDocumentById(book2LocId-1);
            AVmaterial av1 = (AVmaterial) Database.getDocumentById(av1Id);
            AVmaterial av2 = (AVmaterial) Database.getDocumentById(av2Id);

            EventManager eventManager = new EventManager();
            LibTask libTask1 = new LibTask(book1, p1, "checkout", true);
            LibTask libTask2 = new LibTask(book2, p1, "checkout", true);
            LibTask libTask3 = new LibTask(book3, p1, "checkout", true);
            LibTask libTask4 = new LibTask(av1, p1, "checkout", true);
            LibTask libTask5 = new LibTask(book1Cop, p2, "checkout", true);
            LibTask libTask6 = new LibTask(book2Cop, p2, "checkout", true);
            LibTask libTask7 = new LibTask(av2, p2, "checkout",true);

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

            if(patronInfo1.second.size() == 3 && patronInfo2.second.size() == 3 && p1.name.equals(names[0])
                    && p1.phoneNumber.equals(phoneNumbers[0]) && p1.isFacultyMember == isFaculty[0] && p1.address.equals(addresses[0]) &&
                    p2.name.equals(names[1]) && p2.address.equals(addresses[1]) && p2.phoneNumber.equals(phoneNumbers[1]) && p2.isFacultyMember == isFaculty[1]
                    && Database.getDocumentReturnDate(patronInfo1.second.get(0)).equals("2018-04-02") && Database.getDocumentReturnDate(patronInfo1.second.get(1)).equals("2018-04-02") &&
                    Database.getDocumentReturnDate(patronInfo1.second.get(2)).equals("2018-03-19") && Database.getDocumentReturnDate(patronInfo2.second.get(0)).equals("2018-03-26") &&
                    Database.getDocumentReturnDate(patronInfo2.second.get(1)).equals("2018-03-19") && Database.getDocumentReturnDate(patronInfo2.second.get(2)).equals("2018-03-19")){
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
            Patron patron1 = Database.getPatronById(user1ID);
            Patron patron2 = Database.getPatronById(user2ID);
            Book book1 = (Book) Database.getDocumentById(book1LocId);
            Book book2 = (Book) Database.getDocumentById(book2LocId);
            Book book1Cop = (Book) Database.getDocumentById(book1LocId-1);
            AVmaterial av1 = (AVmaterial) Database.getDocumentById(av1Id);
            AVmaterial av2 = (AVmaterial) Database.getDocumentById(av2Id);

            Booking.setDate = February9Date;
            Booking.useCustomDate = true;
            EventManager eventManager = new EventManager();

            LibTask libTask1 = new LibTask(book1, patron1, "checkout", true);
            eventManager.ExecuteQuery(libTask1);

            Booking.setDate = February2Date;
            LibTask libTask2 = new LibTask(book2, patron1, "checkout", true);
            eventManager.ExecuteQuery(libTask2);

            Booking.setDate = February5Date;
            LibTask libTask3 = new LibTask(book1Cop, patron2, "checkout", true);
            eventManager.ExecuteQuery(libTask3);

            Booking.setDate = February17Date;
            LibTask libTask4 = new LibTask(av1, patron2, "checkout", true);
            eventManager.ExecuteQuery(libTask4);

            Booking booking = new Booking();
            Booking.setDate = March5Date;

            ArrayList<Pair<Document, Integer>> user1OverdueDocuments = patron1.getAllOverdueDocuments(lib.id);
            ArrayList<Pair<Document, Integer>> user2OverdueDocuments = patron2.getAllOverdueDocuments(lib.id);

            Booking.useCustomDate = false;

            if(user1OverdueDocuments.get(0).second == 3 && user2OverdueDocuments.get(0).second == 7 && user2OverdueDocuments.get(1).second == 2){
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
//            FileWriter fileWriter = new FileWriter(in);
//            fileWriter.close();
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
                ResultSet rs1 = Database.SelectFromDB("select count(id) from documents");
                rs1.next();
                ResultSet rs2 = Database.SelectFromDB("select count(id) from users");
                rs2.next();

                if(rs1.getInt(1) == 8 && rs2.getInt(1) == 4){
                    System.out.println("TC9 PASSED!");
                    gFw.write("TC9 PASSED!\n");
                }else{
                    System.out.println("TC9 FAILED!");
                    gFw.write("TC9 FAILED!\n");
                }
                gFw.flush();
                gFw.close();
                System.exit(0);
            }
        }catch (Exception e){
            System.out.println("Error in tc9: " + e.toString());
        }
    }

    public void init3(){
        try {
            Statement statement = Database.connection.createStatement();
            String[] tables = {"av_materials",  "books", "journal_articles", "journals", "libtasks", "request", "users", "documents", "booking"};
            for (int i = 0; i < tables.length; i++) {
                Database.DeleteAllInTable(tables[i]);
            }
            Database.ExecuteQuery("INSERT INTO `users` (`id`, `name`, `phoneNumber`, `address`, `debt`, `isFacultyMember`, `password`, `isLibrarian`, `type`) VALUES ('1', 'All cash', '1', '1', '0', b'0', '1', b'1', 'lib');");
            lib = Database.getPatronById(1);

            Book book1 = new Book(title[0], new ArrayList(Arrays.asList(authors[0])), price[0], new ArrayList<>(), false, publisher[0], edition[0], year[0], false, "lib", true);
            book1.CreateDocumentInDB(lib.id);
            book1.addCopies(2, lib.id);
            book1LocId = book1.id;

            Book book2 = new Book(title[1], new ArrayList(Arrays.asList(authors[1])), price[1], new ArrayList<>(), false, publisher[1], edition[1], year[1], true, "lib", true);
            book2.CreateDocumentInDB(lib.id);
            book2.addCopies(2, lib.id);
            book2LocId = book2.id;

            String[][] avAuthors = {{"Tony Hoare"}, {"Claude Shannon"}};
            AVmaterial aVmaterial1 = new AVmaterial(title[2], new ArrayList<String>(Arrays.asList(avAuthors[0])), price[2], new ArrayList<>(), false, true, "loc");
            aVmaterial1.CreateDocumentInDB(lib.id);
            aVmaterial1.addCopies(1, lib.id);
            av1Id = aVmaterial1.id;

            Patron  patron1 = new Patron(names[0], "1", phoneNumbers[0], addresses[0], isFaculty[0], 0, PatronType.professor, true);
            patron1.CreateUserDB();
            user1ID = patron1.id;

            Patron patron2 = new Patron(names[1], "1", phoneNumbers[1], addresses[1], isFaculty[1], 0, PatronType.professor, true);
            patron2.CreateUserDB();
            user2ID = patron2.id;

            Patron patron3 = new Patron(names[2], "1", phoneNumbers[2], addresses[2], isFaculty[2], 0, PatronType.professor, true);
            patron3.CreateUserDB();
            user3ID = patron3.id;

            Patron patron4 = new Patron(names[3], "1", phoneNumbers[3], addresses[3], isFaculty[3], 0, PatronType.student, true);
            patron4.CreateUserDB();
            user4ID = patron4.id;

            Patron patron5 = new Patron(names[4], "1", phoneNumbers[4], addresses[4], isFaculty[4], 0, PatronType.visitingProf, true);
            patron5.CreateUserDB();
            user5ID = patron5.id;

            booksIds.clear();
            patronsIds.clear();

            System.out.println("init3 passed");
        }catch (Exception e){
            System.out.println("Error in init1: " + e.toString());
        }
    }

    public void findUsersAndBooks(){
        try{
            for (int i = 0; i < title.length; i++){
                ArrayList <Document> ds = Database.findDocuments(title[i], "title");
                for (int j = 0; j < ds.size(); j++) {
                    booksIds.add(ds.get(j));
                }
            }

            for (int i = 0; i < names.length; i++){
                Patron p = Database.findPatronBy(names[i], "name");
                patronsIds.add(p);
            }

            System.out.println("findUserAndBooks passed");
        }catch (Exception e){
            System.out.println("Error in findUsersAndBooks: " + e.toString());
        }
    }

    public void clearHistory(){
        try{
            Statement statement = Database.connection.createStatement();
            String[] tables = {"libtasks", "request", "booking"};
            for (int i = 0; i < tables.length; i++) {
                Database.DeleteAllInTable(tables[i]);
            }

            System.out.println("History cleared");
        }catch (Exception e){
            System.out.println("Error in clearHistory: " + e.toString());
        }
    }

    public void t1(){
        try{
            init3();
            findUsersAndBooks();

            Booking.useCustomDate = true;
            CurrentSession.setDate = March5Date;
            LibTask libTask1 = new LibTask(booksIds.get(0), patronsIds.get(0), "checkout", true);
            LibTask libTask2 = new LibTask(booksIds.get(3), patronsIds.get(0), "checkout", true);

            EventManager em = new EventManager();
            libTask1.id = em.CreateQuery(libTask1);
            libTask1.id = em.CreateQuery(libTask2);
            em.ExecuteQuery(libTask1);
            em.ExecuteQuery(libTask2);

            CurrentSession.setDate = April2Date;

            LibTask libTask3 = new LibTask(booksIds.get(3), patronsIds.get(0), "return", true);
            em.ExecuteQuery(libTask3);

            ArrayList<Document> userdocs = Database.getUserDocuments(patronsIds.get(0));
            Booking booking = new Booking();
            int costOverdue = booking.countOverdueCost(userdocs.get(0));
            int countOverdue = booking.countOverdue(userdocs.get(0));
            Booking.useCustomDate = false;

            assert (costOverdue == 0 && countOverdue == 0);
            System.out.println("test1 passed");

        }catch (Exception e){
            System.out.println("Error in t1: "  + e.toString());
        }
    }

    public void t2(){
        try{
            init3();
            findUsersAndBooks();

            Booking.useCustomDate = true;
            CurrentSession.setDate = March5Date;


            ArrayList<LibTask> libTasks = new ArrayList<>();
            libTasks.add(new LibTask(booksIds.get(0), patronsIds.get(0), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(3), patronsIds.get(0), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(1), patronsIds.get(3), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(4), patronsIds.get(3), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(2), patronsIds.get(4), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(5), patronsIds.get(4), "checkout", true));

            EventManager em = new EventManager();
            for (int i = 0; i < libTasks.size(); i++) {
                libTasks.get(i).id =  em.CreateQuery(libTasks.get(i));
            }
            for (int i = 0; i < libTasks.size(); i++) {
                em.ExecuteQuery(libTasks.get(i));
            }

            ArrayList<ArrayList<Document>> usersDocs = new ArrayList<>();
            usersDocs.add(Database.getUserDocuments(patronsIds.get(0)));
            usersDocs.add(Database.getUserDocuments(patronsIds.get(3)));
            usersDocs.add(Database.getUserDocuments(patronsIds.get(4)));

            Booking booking = new Booking();
            CurrentSession.setDate = April2Date;

            ArrayList<Pair<Integer, Integer>> cost_day = new ArrayList<>();
            cost_day.add(new Pair<Integer, Integer>(booking.countOverdueCost(booksIds.get(0)), booking.countOverdue(booksIds.get(0))));
            cost_day.add(new Pair<Integer, Integer>(booking.countOverdueCost(booksIds.get(3)), booking.countOverdue(booksIds.get(3))));
            cost_day.add(new Pair<Integer, Integer>(booking.countOverdueCost(booksIds.get(1)), booking.countOverdue(booksIds.get(1))));
            cost_day.add(new Pair<Integer, Integer>(booking.countOverdueCost(booksIds.get(4)), booking.countOverdue(booksIds.get(4))));
            cost_day.add(new Pair<Integer, Integer>(booking.countOverdueCost(booksIds.get(2)), booking.countOverdue(booksIds.get(2))));
            cost_day.add(new Pair<Integer, Integer>(booking.countOverdueCost(booksIds.get(5)), booking.countOverdue(booksIds.get(5))));

            Booking.useCustomDate = false;

            assert (cost_day.get(0).first == 0 && cost_day.get(0).second == 0 &&
                    cost_day.get(1).first == 0 && cost_day.get(1).second == 0 &&
                    cost_day.get(2).first == 700 && cost_day.get(2).second == 7 &&
                    cost_day.get(3).first == 1400 && cost_day.get(3).second == 14 &&
                    cost_day.get(4).first == 2100 && cost_day.get(4).second == 21 &&
                    cost_day.get(5).first == 1700 && cost_day.get(5).second == 21);
            System.out.println("test2 passed");


        }catch (Exception e){
            System.out.println("Error in t2: "  + e.toString());
        }
    }

    public void t3(){
        try{
            Booking.useCustomDate = true;
            CurrentSession.setDate = March29Date;
            init3();
            findUsersAndBooks();

            ArrayList<LibTask> libTasks = new ArrayList<>();
            libTasks.add(new LibTask(booksIds.get(0), patronsIds.get(0), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(3), patronsIds.get(3), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(4), patronsIds.get(4), "checkout", true));

            EventManager em = new EventManager();
            for (int i = 0; i < libTasks.size(); i++) {
                em.ExecuteQuery(libTasks.get(i));
            }

            CurrentSession.setDate = April2Date;
            ArrayList<LibTask> renewLibTasks = new ArrayList<>();
            renewLibTasks.add(new LibTask(booksIds.get(0), patronsIds.get(0), "renew", true));
            renewLibTasks.add(new LibTask(booksIds.get(3), patronsIds.get(3), "renew", true));
            renewLibTasks.add(new LibTask(booksIds.get(4), patronsIds.get(4), "renew", true));
            for (int i = 0; i < renewLibTasks.size(); i++) {
                em.ExecuteQuery(renewLibTasks.get(i));
            }

            ArrayList<String> returnDates = new ArrayList<>();
            returnDates.add(Database.getDocumentReturnDate(booksIds.get(0)));
            returnDates.add(Database.getDocumentReturnDate(booksIds.get(3)));
            returnDates.add(Database.getDocumentReturnDate(booksIds.get(4)));
            Booking.useCustomDate = false;

            //Expected Result
            assert (returnDates.get(0).equals("2018-04-30") &&
                    returnDates.get(1).equals("2018-04-16") &&
                    returnDates.get(2).equals("2018-04-09"));
            System.out.println("test3 passed");


        }catch (Exception e){
            System.out.println("Error in t3: "  + e.toString());
        }
    }

    public void t4(){
        try{
            Booking.useCustomDate = true;
            CurrentSession.setDate = March29Date;
            init3();
            findUsersAndBooks();

            ArrayList<LibTask> libTasks = new ArrayList<>();
            libTasks.add(new LibTask(booksIds.get(0), patronsIds.get(0), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(3), patronsIds.get(3), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(4), patronsIds.get(4), "checkout", true));

            EventManager em = new EventManager();
            for (int i = 0; i < libTasks.size(); i++) {
                em.ExecuteQuery(libTasks.get(i));
            }

            CurrentSession.setDate = April2Date;
            Librarian librarian = new Librarian();
            librarian.sendOutstandingRequest(booksIds.get(3));

            Booking.setDate = April2Date;
            ArrayList<LibTask> renewLibTasks = new ArrayList<>();
            renewLibTasks.add(new LibTask(booksIds.get(0), patronsIds.get(0), "renew", true));
            renewLibTasks.add(new LibTask(booksIds.get(3), patronsIds.get(3), "renew", true));
            renewLibTasks.add(new LibTask(booksIds.get(4), patronsIds.get(4), "renew", true));
            for (int i = 0; i < renewLibTasks.size(); i++) {
                em.ExecuteQuery(renewLibTasks.get(i));
            }

            ArrayList<String> returnDates = new ArrayList<>();
            returnDates.add(Database.getDocumentReturnDate(booksIds.get(0)));
            returnDates.add(Database.getDocumentReturnDate(booksIds.get(3)));
            returnDates.add(Database.getDocumentReturnDate(booksIds.get(4)));
            Booking.useCustomDate = false;

            //Expected Result
            assert (returnDates.get(0).equals("2018-04-30") &&
                    returnDates.get(1).equals("2018-04-02") &&
                    returnDates.get(2).equals("2018-04-02"));
            System.out.println("test4 passed");

        }catch (Exception e){
            System.out.println("Error in t4: "  + e.toString());
        }
    }

    public void t5(){
        try{
            CurrentSession.setDate = April2Date;
            init3();
            findUsersAndBooks();

            ArrayList<LibTask> libTasks = new ArrayList<>();
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(0), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(3), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(4), "checkout", true));

            EventManager em = new EventManager();
            for (int i = 0; i < libTasks.size(); i++) {
                libTasks.get(i).id = em.CreateQuery(libTasks.get(i));
            }
            for (int i = 0; i < libTasks.size(); i++) {
                em.ExecuteQuery(libTasks.get(i));
            }

            ArrayList<Patron> patrons = Database.getDocumentQueue(booksIds.get(6));

            //Expected Result
            assert (patrons.get(0).id == patronsIds.get(4).id);
            System.out.println("test5 passed");


        }catch (Exception e){
            System.out.println("Error in t5: "  + e.toString());
        }
    }

    public void t6(){
        try{
            CurrentSession.setDate = April2Date;
            Booking.useCustomDate = true;
            init3();
            findUsersAndBooks();

            ArrayList<LibTask> libTasks = new ArrayList<>();
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(0), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(1), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(3), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(4), "checkout", true));
            libTasks.add(new LibTask(booksIds.get(6), patronsIds.get(2), "checkout", true));

            EventManager em = new EventManager();
            for (int i = 0; i < libTasks.size(); i++) {
                libTasks.get(i).id = em.CreateQuery(libTasks.get(i));
            }
            for (int i = 0; i < libTasks.size(); i++) {
                em.ExecuteQuery(libTasks.get(i));
            }

            ArrayList<Patron> patrons = Database.getDocumentQueue(booksIds.get(6));

            assert (patrons.get(0).id == patronsIds.get(3).id && patrons.get(1).id == patronsIds.get(4).id && patrons.get(2).id == patronsIds.get(2).id);
            System.out.println("test6 passed");

        }catch (Exception e){
            System.out.println("Error in t6: "  + e.toString());
        }
    }

    public void t7(){
        try{
            t6();

            Librarian librarian = new Librarian();
            librarian.sendOutstandingRequest(booksIds.get(6));

            ArrayList<Patron> patrons = Database.getDocumentQueue(booksIds.get(6));
            ArrayList<String> messages = new ArrayList<>();

            for (int i = 0; i < patronsIds.size(); i++) {

                ResultSet rs = Database.SelectFromDB("select * from request where id_user = " + patronsIds.get(i).id);
                rs.next();
                messages.add(rs.getString("message"));
            }

            assert (messages.get(0).equals(RequestsText.return_book_en) &&
                    messages.get(1).equals(RequestsText.return_book_en) &&
                    messages.get(2).equals(RequestsText.removed_queue_en) &&
                    messages.get(3).equals(RequestsText.removed_queue_en) &&
                    messages.get(4).equals(RequestsText.removed_queue_en) && patrons.size() == 0);

            System.out.println("test7 passed");

        }catch (Exception e){
            System.out.println("Error in t7: "  + e.toString());
        }
    }

    public void t8(){
        try{
            t6();

            EventManager em = new EventManager();
            LibTask returnDoc = new LibTask(booksIds.get(6), patronsIds.get(1), "return", true);
            returnDoc.id = em.CreateQuery(returnDoc);
            em.ExecuteQuery(returnDoc);

            ResultSet rs = Database.SelectFromDB("select * from request where id_user = " + patronsIds.get(3).id);
            rs.next();
            String message = rs.getString("message");
            ArrayList<Document> p2Docs = Database.getUserDocuments(patronsIds.get(1));
            ArrayList<Patron> patrons = Database.getDocumentQueue(booksIds.get(6));

            assert (message.equals(RequestsText.get_book_en) && p2Docs.size() == 0 &&
                    patrons.get(0).id == patronsIds.get(3).id && patrons.get(1).id == patronsIds.get(4).id && patrons.get(2).id == patronsIds.get(2).id);

            System.out.println("test8 passed");
        }catch (Exception e){
            System.out.println("Error in t8: "  + e.toString());
        }
    }

    public void t9(){
        try{
            t6();

            EventManager em = new EventManager();
            CurrentSession.setDate = April16Date;
            Booking.useCustomDate = true;
            LibTask libTask = new LibTask(booksIds.get(6), patronsIds.get(0), "renew", true);
            libTask.id = em.CreateQuery(libTask);
            em.ExecuteQuery(libTask);
            String date = Database.getDocumentReturnDate(booksIds.get(6));
            ArrayList<Patron> patrons = Database.getDocumentQueue(booksIds.get(6));

            assert (date.equals("2018-04-30") &&
                    patrons.get(0).id == patronsIds.get(3).id && patrons.get(1).id == patronsIds.get(4).id && patrons.get(2).id == patronsIds.get(2).id);
            System.out.println("test9 passed");

        }catch (Exception e){
            System.out.println("Error in t9: "  + e.toString());
        }
    }

    public void t10(){
        try{
            init3();
            findUsersAndBooks();

            Booking.useCustomDate = true;
            CurrentSession.setDate = March26Date;
            EventManager em = new EventManager();

            LibTask p1chec = new LibTask(booksIds.get(0), patronsIds.get(0), "checkout", true);
            p1chec.id = em.CreateQuery(p1chec);
            em.ExecuteQuery(p1chec);

            CurrentSession.setDate = March29Date;
            LibTask p1re = new LibTask(booksIds.get(0), patronsIds.get(0), "renew", true);
            p1re.id = em.CreateQuery(p1re);
            em.ExecuteQuery(p1re);

            CurrentSession.setDate = March26Date;
            LibTask v1chec = new LibTask(booksIds.get(1), patronsIds.get(4), "checkout", true);
            v1chec.id = em.CreateQuery(v1chec);
            em.ExecuteQuery(v1chec);

            CurrentSession.setDate = March29Date;
            LibTask v1re = new LibTask(booksIds.get(1), patronsIds.get(4), "renew", true);
            v1re.id = em.CreateQuery(v1re);
            em.ExecuteQuery(v1re);

            String date1 = Database.getDocumentReturnDate(booksIds.get(0));
            String date2 = Database.getDocumentReturnDate(booksIds.get(1));

            assert (date1.equals("2018-04-26") && date2.equals("2018-04-05"));

            System.out.println("test10 passed");
        }catch (Exception e){
            System.out.println("Error in t10: "  + e.toString());
        }
    }
}
