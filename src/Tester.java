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

    private String[] bnames = {"Introduction to Algorithms", "Algorithms + Data Structures = Programs", "The Art of Computer Programming"};
    private String[][] bauthors = {{"Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest"}, {"Niklaus Wirth"}, {"Donald E. Knuth"}};
    private String[] bpublisher = {"MIT Press", "Prentice Hall PTR", "Addison Wesley Longman Publishing Co., Inc."};
    private Integer[] byear = {2009, 1978, 1997};
    private String[] bedition = {"Third edition", "First edition", "Third edition"};
    private Integer[] bprice = {5000, 5000, 5000};
    private Boolean[] bIsBestseller = {false, false};
    private String[][] bKeywords = {{"Algorithms", "Data Structures", "Complexity", "Computational Theory"},
            {"Algorithms", "Data Structures", "Search Algorithms", "Pascal"},
            {"Algorithms", "Combinatorial Algorithms", "Recursion"}};

    private Admin admin;
    private Boolean[] isFaculty = {true, true, true, false, false};
    private Librarian[] libs;
    private Book[][] t4_books;
    private Patron[] p4_patrons;

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
            Database.ExecuteQuery("INSERT INTO `users` (`id`, `name`, `phoneNumber`, `address`, `debt`, `isFacultyMember`, `password`, `isLibrarian`, `type`) VALUES ('1', 'All cash', '1', '1', '0', b'0', '1', b'1', 'admin');");
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
            patron1.CreateUserDB(admin.id);
            user1ID = patron1.id;

            Patron patron2 = new Patron(names[1], "1", phoneNumbers[1], addresses[1], isFaculty[1], 0, PatronType.student, true);
            patron2.CreateUserDB(admin.id);
            user2ID = patron2.id;

            Patron patron3 = new Patron(names[2], "1", phoneNumbers[2], addresses[2], isFaculty[2], 0, PatronType.student, true);
            patron3.CreateUserDB(admin.id);
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
            Database.ExecuteQuery("INSERT INTO `users` (`id`, `name`, `phoneNumber`, `address`, `debt`, `isFacultyMember`, `password`, `isLibrarian`, `type`) VALUES ('1', 'All cash', '1', '1', '0', b'0', '1', b'1', 'admin');");
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
            patron1.CreateUserDB(admin.id);
            user1ID = patron1.id;

            Patron patron2 = new Patron(names[1], "1", phoneNumbers[1], addresses[1], isFaculty[1], 0, PatronType.professor, true);
            patron2.CreateUserDB(admin.id);
            user2ID = patron2.id;

            Patron patron3 = new Patron(names[2], "1", phoneNumbers[2], addresses[2], isFaculty[2], 0, PatronType.professor, true);
            patron3.CreateUserDB(admin.id);
            user3ID = patron3.id;

            Patron patron4 = new Patron(names[3], "1", phoneNumbers[3], addresses[3], isFaculty[3], 0, PatronType.student, true);
            patron4.CreateUserDB(admin.id);
            user4ID = patron4.id;

            Patron patron5 = new Patron(names[4], "1", phoneNumbers[4], addresses[4], isFaculty[4], 0, PatronType.visitingProf, true);
            patron5.CreateUserDB(admin.id);
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
            Database.sendOutstandingRequest(booksIds.get(3),librarian);

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
            Database.sendOutstandingRequest(booksIds.get(6), librarian);

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

    public void init4(){
        try{
            Statement statement = Database.connection.createStatement();
            String[] tables = {"av_materials",  "books", "journal_articles", "journals", "libtasks", "request", "users", "documents", "booking", "logging"};
            for (int i = 0; i < tables.length; i++) {
                Database.DeleteAllInTable(tables[i]);
            }

            admin = new Admin("admin", "1", "address");
            admin.CreateUserDB(0);

        }catch (Exception e){
            System.out.println("Error in init4: " + e.toString());
        }
    }

    public void t41(){
        try{
            Admin admin2 = new Admin("admin", "1", "address");
            admin2.CreateUserDB(0);

            int amount_of_users = 0;
            ResultSet rs = Database.SelectFromDB("select * from users");
            String user_type = "";
            while(rs.next()){
                amount_of_users++;
                user_type=rs.getString("type");
            }

            assert (amount_of_users == 1 && user_type.equals("admin"));
            System.out.println("Test 1 passed");
        }catch (Exception e){
            System.out.println("Error in t41: " + e.toString());
        }
    }

    public void t42(){
        try{
            libs = new Librarian[3];
            //String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, PatronType type, boolean isActive
            libs[0] = new Librarian("lib1", "1", "11", "add", LibrarianType.Priv1);
            libs[0].CreateUserDB(admin.id);
            Database.upgradeToLibrarian(libs[0], "priv1", admin.id);
            libs[1] = new Librarian("lib2", "1", "12", "add", LibrarianType.Priv2);
            libs[1].CreateUserDB(admin.id);
            Database.upgradeToLibrarian(libs[1], "priv2", admin.id);
            libs[2] = new Librarian("lib3", "1", "13", "add", LibrarianType.Priv3);
            libs[2].CreateUserDB(admin.id);
            Database.upgradeToLibrarian(libs[2], "priv3", admin.id);

            int amount_of_users = 0;
            ResultSet rs = Database.SelectFromDB("select * from users");
            String[] user_types = new String[4];
            int counter = 0;
            while(rs.next()){
                user_types[counter] = rs.getString("type");
                amount_of_users++;
                counter++;
            }

            assert (amount_of_users == 4 && user_types[0].equals("admin") && user_types[1].equals("priv1") && user_types[2].equals("priv2")
             && user_types[3].equals("priv3"));

            System.out.println("Test 2 passed");
        }catch (Exception e){
            System.out.println("Error in t42: " + e.toString());
        }
    }

    public void t43(){
        try{
            init4();
            t42();
            t4_books = new Book[3][3];
            t4_books[0][0] = new Book(bnames[0], new ArrayList(Arrays.asList(bauthors[0])), bprice[0], new ArrayList(Arrays.asList(bKeywords[0])), false, bpublisher[0], bedition[0], byear[0], false, "lib", true);
            t4_books[0][0].CreateDocumentInDB(libs[0].id);
            ArrayList<Document> alb1 = t4_books[0][0].addCopies(2, libs[0].id);
            if(alb1.size() > 0) {
                t4_books[0][1] = (Book) alb1.get(0);
                t4_books[0][2] = (Book) alb1.get(1);
            }

            t4_books[1][0] = new Book(bnames[0], new ArrayList(Arrays.asList(bauthors[0])), bprice[0], new ArrayList(Arrays.asList(bKeywords[0])), false, bpublisher[0], bedition[0], byear[0], false, "lib", true);
            t4_books[1][0].CreateDocumentInDB(libs[0].id);
            alb1 = t4_books[1][0].addCopies(2, libs[0].id);
            if(alb1.size() > 0) {
                t4_books[1][1] = (Book) alb1.get(0);
                t4_books[1][2] = (Book) alb1.get(1);
            }

            t4_books[2][0] = new Book(bnames[0], new ArrayList(Arrays.asList(bauthors[0])), bprice[0], new ArrayList(Arrays.asList(bKeywords[0])), false, bpublisher[0], bedition[0], byear[0], false, "lib", true);
            t4_books[2][0].CreateDocumentInDB(libs[0].id);
            alb1 = t4_books[2][0].addCopies(2, libs[0].id);
            if(alb1.size() > 0) {
                t4_books[2][1] = (Book) alb1.get(0);
                t4_books[2][2] = (Book) alb1.get(1);
            }

            ArrayList<Document> docs = Database.getAllDocuments();

            assert (docs.size() == 0);

            System.out.println("Test 3 passed");
        }catch (Exception e){
            System.out.println("Error in t43: " + e.toString());
        }
    }

    public void t44(){
        try{

            init4();
            t42();
            t4_books = new Book[3][3];
            t4_books[0][0] = new Book(bnames[0], new ArrayList(Arrays.asList(bauthors[0])), bprice[0], new ArrayList(Arrays.asList(bKeywords[0])), false, bpublisher[0], bedition[0], byear[0], false, "lib", true);
            t4_books[0][0].CreateDocumentInDB(libs[1].id);
            ArrayList<Document> alb1 = t4_books[0][0].addCopies(2, libs[1].id);
            if(alb1.size() > 0) {
                t4_books[0][1] = (Book) alb1.get(0);
                t4_books[0][2] = (Book) alb1.get(1);
            }

            t4_books[1][0] = new Book(bnames[1], new ArrayList(Arrays.asList(bauthors[1])), bprice[1], new ArrayList(Arrays.asList(bKeywords[1])), false, bpublisher[1], bedition[1], byear[1], false, "lib", true);
            t4_books[1][0].CreateDocumentInDB(libs[1].id);
            alb1 = t4_books[1][0].addCopies(2, libs[1].id);
            if(alb1.size() > 0) {
                t4_books[1][1] = (Book) alb1.get(0);
                t4_books[1][2] = (Book) alb1.get(1);
            }

            t4_books[2][0] = new Book(bnames[2], new ArrayList(Arrays.asList(bauthors[2])), bprice[2], new ArrayList(Arrays.asList(bKeywords[2])), false, bpublisher[2], bedition[2], byear[2], false, "lib", true);
            t4_books[2][0].CreateDocumentInDB(libs[1].id);
            alb1 = t4_books[2][0].addCopies(2, libs[1].id);
            if(alb1.size() > 0) {
                t4_books[2][1] = (Book) alb1.get(0);
                t4_books[2][2] = (Book) alb1.get(1);
            }

            p4_patrons = new Patron[5];

            p4_patrons[0] = new Patron(names[0], "1", phoneNumbers[0], addresses[0], isFaculty[0], 0, PatronType.professor, true);
            p4_patrons[0].CreateUserDB(libs[1].id);
            user1ID = p4_patrons[0].id;

            p4_patrons[1] = new Patron(names[1], "1", phoneNumbers[1], addresses[1], isFaculty[1], 0, PatronType.professor, true);
            p4_patrons[1].CreateUserDB(libs[1].id);
            user2ID = p4_patrons[1].id;

            p4_patrons[2] = new Patron(names[2], "1", phoneNumbers[2], addresses[2], isFaculty[2], 0, PatronType.professor, true);
            p4_patrons[2].CreateUserDB(libs[1].id);
            user3ID = p4_patrons[2].id;

            p4_patrons[3] = new Patron(names[3], "1", phoneNumbers[3], addresses[3], isFaculty[3], 0, PatronType.student, true);
            p4_patrons[3].CreateUserDB(libs[1].id);
            user4ID = p4_patrons[3].id;

            p4_patrons[4] = new Patron(names[4], "1", phoneNumbers[4], addresses[4], isFaculty[4], 0, PatronType.visitingProf, true);
            p4_patrons[4].CreateUserDB(libs[1].id);
            user5ID = p4_patrons[4].id;

            ArrayList<Document> docs = Database.getAllDocuments();
            ArrayList<Patron> patrons = Database.getAllPatrons();

            assert (docs.size() == 9 && patrons.size() == 5);

            System.out.println("Test 4 passed");
        }catch (Exception e){
            System.out.println("Error in t44: " + e.toString());
        }
    }

    public void t45(){
        try{
            t44();
            t4_books[0][2].deleteCopies(1, libs[2].id);
            t4_books[0][2] = null;

            assert (Database.getAmountOfCurrentDocument(t4_books[0][1]) == 2);

            System.out.println("Test 5 passed");
        }catch (Exception e){
            System.out.println("Error in t45: " + e.toString());
        }
    }

    public void t46(){
        try{
            t44();

            LibTask[] libTasks = new LibTask[5];
            libTasks[0] = new LibTask(t4_books[2][0], p4_patrons[0], "checkout", true);
            libTasks[1] = new LibTask(t4_books[2][0], p4_patrons[1], "checkout", true);
            libTasks[2] = new LibTask(t4_books[2][0], p4_patrons[2], "checkout", true);
            libTasks[3] = new LibTask(t4_books[2][0], p4_patrons[3], "checkout", true);
            libTasks[4] = new LibTask(t4_books[2][0], p4_patrons[4], "checkout", true);

            EventManager em = new EventManager();

            for (int i = 0; i < 5; i++) {
               libTasks[i].id = em.CreateQuery(libTasks[i]);
            }
            for (int i = 0; i < 5; i++) {
                em.ExecuteQuery(libTasks[i]);
            }

            Database.sendOutstandingRequest(t4_books[2][0], libs[0]);

            ResultSet rs = Database.SelectFromDB("select * from request");
            int amountOfRequest = 0;
            while(rs.next()){
                amountOfRequest++;
            }

            assert (amountOfRequest == 0);

            System.out.println("Test 6 passed");
        }catch (Exception e){
            System.out.println("Error in t46: " + e.toString());
        }
    }

    public void t47(){
        try{
            t44();

            LibTask[] libTasks = new LibTask[5];
            libTasks[0] = new LibTask(t4_books[2][0], p4_patrons[0], "checkout", true);
            libTasks[1] = new LibTask(t4_books[2][0], p4_patrons[1], "checkout", true);
            libTasks[2] = new LibTask(t4_books[2][0], p4_patrons[2], "checkout", true);
            libTasks[3] = new LibTask(t4_books[2][0], p4_patrons[3], "checkout", true);
            libTasks[4] = new LibTask(t4_books[2][0], p4_patrons[4], "checkout", true);

            EventManager em = new EventManager();

            for (int i = 0; i < 5; i++) {
                libTasks[i].id = em.CreateQuery(libTasks[i]);
            }
            for (int i = 0; i < 5; i++) {
                em.ExecuteQuery(libTasks[i]);
            }

            Database.sendOutstandingRequest(t4_books[2][0], libs[1]);

            ResultSet rs = Database.SelectFromDB("select * from request");
            int amountOfRequest = 0;
            String[] messages = new String[5];
            while(rs.next()){
                messages[amountOfRequest] = rs.getString("message");
                amountOfRequest++;
            }

            rs = Database.SelectFromDB("select * from libtasks");
            int amountOfLibtasks = 0;
            while (rs.next()){
                amountOfLibtasks++;
            }

            assert (amountOfRequest == 5 && amountOfLibtasks == 0 && messages[0].equals(RequestsText.removed_queue_en) &&
                messages[1].equals(RequestsText.removed_queue_en) && messages[2].equals(RequestsText.return_book_en) &&
                messages[3].equals(RequestsText.return_book_en) && messages[4].equals(RequestsText.return_book_en));

            System.out.println("Test 7 passed");
        }catch (Exception e){
            System.out.println("Error in t47: " + e.toString());
        }
    }

    public void t48(){
        try{
            t46();
            ArrayList<String> logs = Logging.getLast(100);
            ArrayList<ArrayList<String>> rlogs = new ArrayList<>();
            for (int i = logs.size()-1; i >= 0; i--) {
                //new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", ")))
                rlogs.add(new ArrayList<String>(Arrays.asList(logs.get(i).split("#"))));
            }

            boolean isAllHave = false;

            if(rlogs.get(0).get(1).equals("create lib1") && !rlogs.get(0).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(0).get(0)) == admin.id &&
                    rlogs.get(1).get(1).equals("create lib2") && !rlogs.get(1).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(1).get(0)) == admin.id &&
                    rlogs.get(2).get(1).equals("create lib3") && !rlogs.get(2).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(2).get(0)) == admin.id &&
                    rlogs.get(3).get(1).equals("addCopies book Introduction to Algorithms") && !rlogs.get(3).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(3).get(0)) == libs[1].id &&
                    rlogs.get(4).get(1).equals("addCopies book Algorithms + Data Structures = Programs") && !rlogs.get(4).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(4).get(0)) == libs[1].id &&
                    rlogs.get(5).get(1).equals("addCopies book The Art of Computer Programming") && !rlogs.get(5).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(5).get(0)) == libs[1].id &&
                    rlogs.get(6).get(1).equals("created patron Sergey Afonso") && !rlogs.get(6).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(6).get(0)) == libs[1].id &&
                    rlogs.get(7).get(1).equals("created patron Nadia Teixeira") && !rlogs.get(7).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(7).get(0)) == libs[1].id &&
                    rlogs.get(8).get(1).equals("created patron Elvira Espindola") && !rlogs.get(8).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(8).get(0)) == libs[1].id &&
                    rlogs.get(9).get(1).equals("created patron Andrey Velo") && !rlogs.get(9).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(9).get(0)) == libs[1].id &&
                    rlogs.get(10).get(1).equals("created patron Veronika Rama") && !rlogs.get(10).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(10).get(0)) == libs[1].id &&
                    rlogs.get(11).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(11).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(11).get(0)) == p4_patrons[0].id &&
                    rlogs.get(12).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(12).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(12).get(0)) == p4_patrons[1].id &&
                    rlogs.get(13).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(13).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(13).get(0)) == p4_patrons[2].id &&
                    rlogs.get(14).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(14).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(14).get(0)) == p4_patrons[3].id &&
                    rlogs.get(15).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(15).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(15).get(0)) == p4_patrons[4].id &&
                    rlogs.get(16).get(1).equals("outstanding request on document The Art of Computer Programming: request was denied.") && !rlogs.get(16).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(16).get(0)) == libs[0].id){
                isAllHave = true;
            }

//            System.out.println(rlogs.toString());

            assert(isAllHave);

            System.out.println("Test 8 passed");
        }catch (Exception e){
            System.out.println("Error in t48: " + e.toString());
        }
    }

    public void t49(){
        try{
            t47();
            ArrayList<String> logs = Logging.getLast(100);
            ArrayList<ArrayList<String>> rlogs = new ArrayList<>();
            for (int i = logs.size()-1; i >= 0; i--) {
                //new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", ")))
                rlogs.add(new ArrayList<String>(Arrays.asList(logs.get(i).split("#"))));
            }

            boolean isAllHave = false;

            if(rlogs.get(0).get(1).equals("create lib1") && !rlogs.get(0).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(0).get(0)) == admin.id &&
                    rlogs.get(1).get(1).equals("create lib2") && !rlogs.get(1).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(1).get(0)) == admin.id &&
                    rlogs.get(2).get(1).equals("create lib3") && !rlogs.get(2).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(2).get(0)) == admin.id &&
                    rlogs.get(3).get(1).equals("addCopies book Introduction to Algorithms") && !rlogs.get(3).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(3).get(0)) == libs[1].id &&
                    rlogs.get(4).get(1).equals("addCopies book Algorithms + Data Structures = Programs") && !rlogs.get(4).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(4).get(0)) == libs[1].id &&
                    rlogs.get(5).get(1).equals("addCopies book The Art of Computer Programming") && !rlogs.get(5).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(5).get(0)) == libs[1].id &&
                    rlogs.get(6).get(1).equals("created patron Sergey Afonso") && !rlogs.get(6).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(6).get(0)) == libs[1].id &&
                    rlogs.get(7).get(1).equals("created patron Nadia Teixeira") && !rlogs.get(7).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(7).get(0)) == libs[1].id &&
                    rlogs.get(8).get(1).equals("created patron Elvira Espindola") && !rlogs.get(8).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(8).get(0)) == libs[1].id &&
                    rlogs.get(9).get(1).equals("created patron Andrey Velo") && !rlogs.get(9).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(9).get(0)) == libs[1].id &&
                    rlogs.get(10).get(1).equals("created patron Veronika Rama") && !rlogs.get(10).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(10).get(0)) == libs[1].id &&
                    rlogs.get(11).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(11).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(11).get(0)) == p4_patrons[0].id &&
                    rlogs.get(12).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(12).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(12).get(0)) == p4_patrons[1].id &&
                    rlogs.get(13).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(13).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(13).get(0)) == p4_patrons[2].id &&
                    rlogs.get(14).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(14).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(14).get(0)) == p4_patrons[3].id &&
                    rlogs.get(15).get(1).equals("checkout The Art of Computer Programming") && !rlogs.get(15).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(15).get(0)) == p4_patrons[4].id &&
                    rlogs.get(16).get(1).equals("removed from queue") && !rlogs.get(16).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(16).get(0)) == libs[1].id &&
                    rlogs.get(17).get(1).equals("removed from queue") && !rlogs.get(17).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(17).get(0)) == libs[1].id &&
                    rlogs.get(18).get(1).equals("waiting list for document The Art of Computer Programming deleted") && !rlogs.get(18).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(19).get(0)) == libs[1].id &&
                    rlogs.get(19).get(1).equals("notified to return book") && !rlogs.get(19).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(19).get(0)) == libs[1].id &&
                    rlogs.get(20).get(1).equals("notified to return book") && !rlogs.get(20).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(20).get(0)) == libs[1].id &&
                    rlogs.get(21).get(1).equals("notified to return book") && !rlogs.get(21).get(2).equals("1970-01-01") && Integer.parseInt(rlogs.get(21).get(0)) == libs[1].id &&
                    rlogs.get(22).get(1).equals("outstanding request on document The Art of Computer Programming: accepted") && !rlogs.get(22).get(2).equals("1970-01-01")&& Integer.parseInt(rlogs.get(22).get(0)) == libs[1].id){
                isAllHave = true;
            }

            assert(isAllHave);

            System.out.println("Test 9 passed");
        }catch (Exception e){
            System.out.println("Error in t49: " + e.toString());
        }
    }

    public void t410(){
        try{
            t44();

            ArrayList<String> documents = Database.searchInDocuments("Introduction to Algorithms", "title");

            assert(documents.get(0).equals("Introduction to Algorithms"));

            System.out.println("Test 10 passed");
        }catch (Exception e){
            System.out.println("Error in t410: " + e.toString());
        }
    }

    public void t411(){
        try{
            t44();

            ArrayList<String> documents = Database.searchInDocuments("Algorithms", "title");

            assert (documents.get(0).equals("Introduction to Algorithms") &&
                documents.get(1).equals("Algorithms + Data Structures = Programs"));

            System.out.println("Test 11 passed");
        }catch (Exception e){
            System.out.println("Error in t411: " + e.toString());
        }
    }

    public void t412(){
        try{
            t44();

            ArrayList<String> documents = Database.searchInDocuments("Algorithms", "keywords");

            assert (documents.get(0).equals("Introduction to Algorithms") &&
                    documents.get(1).equals("Algorithms + Data Structures = Programs") &&
                    documents.get(2).equals("The Art of Computer Programming"));

            System.out.println("Test 12 passed");
        }catch (Exception e){
            System.out.println("Error in t412: " + e.toString());
        }
    }

    public void t413(){
        try{
            t44();

            ArrayList<String> documents = Database.searchInDocuments(" Algorithms AND Programming", "title");

            assert (documents.size() == 0);

            System.out.println("Test 13 passed");
        }catch (Exception e){
            System.out.println("Error in t413: " + e.toString());
        }
    }

    public void t414(){
        try{
            t44();

            ArrayList<String> documents = Database.searchInDocuments(" Algorithms OR Programming", "title");

            assert (documents.get(0).equals("Introduction to Algorithms") &&
                    documents.get(1).equals("Algorithms + Data Structures = Programs") &&
                    documents.get(2).equals("The Art of Computer Programming"));

            System.out.println("Test 14 passed");
        }catch (Exception e){
            System.out.println("Error in t414: " + e.toString());
        }
    }
}
