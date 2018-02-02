import javax.print.Doc;
import javax.print.attribute.standard.DocumentName;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydbtest?useSSL=false";
    private static final String user = "admin";
    private static final String password = "FJ`;62LfOTVZoM2+;3Qo983_zq9iGix9S107pi6)|CzU2`rdVRZD7?5a65sM;|6'54FE\\w9t4Ph~=";

    public static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    public Database(){
        preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }catch (Exception ex){
            System.out.println("error creating db: " + ex.toString());
        }
    }

    public static int isDocumentExist(Document document){
        try {
            int isDocExist = -1;

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id_av_materials, id_books, id_journals, type from documents");

            while (resultSet.next()){
                int id=0;
                if(resultSet.getInt(1) != 0){
                    id = resultSet.getInt(1);
                }else if(resultSet.getInt(2) != 0){
                    id = resultSet.getInt(2);
                }else if(resultSet.getInt(3) != 0){
                    id = resultSet.getInt(3);
                }
                String searchQuery = "select title, author, id from " + resultSet.getString(4) + " where id = " + Integer.toString(id);
                Statement searchStatement = connection.createStatement();
                ResultSet searchResultSet = searchStatement.executeQuery(searchQuery);

                if(searchResultSet.next()) {
                    System.out.println(searchResultSet.getString(1) + " " +searchResultSet.getString(2));
                    if (searchResultSet.getString(1).equals(document.name) && searchResultSet.getString(2).equals(document.authors.toString())) {
                        isDocExist = searchResultSet.getInt(3);
                        break;
                    }
                }
            }

            return isDocExist;
        }catch (Exception ex){
            System.out.println("error isDocExist: " + ex.toString());
            return -1;
        }
    }

    public static int isDocumentExistByType(String type, Document document) throws SQLException{

        if(type.equals("journal")) {
            Journal journal = (Journal) document;

            String query = "select title, author, issue, editor, keywords, reference, id from " + type;

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            resultSet.next();
            if (resultSet.getString(1).equals(journal.name) && resultSet.getString(2).equals(journal.authors.toString())
                    && resultSet.getString(3).equals(journal.issue) && resultSet.getString(4).equals(journal.editor)
                    && resultSet.getInt(5) == journal.price && resultSet.getString(6).equals(journal.keywords.toString())
                    && resultSet.getBoolean(7) == journal.isReference) {
                return resultSet.getInt(8);
            } else {
                return -1;
            }
        }else if(type.equals("book")){
            Book book = (Book) document;

            String query = "select * from " + type;

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                if (resultSet.getString(2).equals(book.name) && resultSet.getString(3).equals(book.authors.toString())
                        && resultSet.getString(4).equals(book.publisher) && resultSet.getString(5).equals(book.edition)
                        && resultSet.getInt(6) == book.publishYear && resultSet.getInt(7) == book.price
                        && resultSet.getString(8).equals(book.keywords.toString()) && resultSet.getBoolean(10) == book.isReference) {
                    return resultSet.getInt(1);
                } else {
                    return -1;
                }
            }
        }else if(type.equals("avmaterial")) {
            return -1;
        }

        return -1;
    }

    public static boolean isDocumentActive(String type, Document document){
        try {
            String query = "select * from documents ";
            boolean isExist;
            resultSet = statement.executeQuery(query);

            if(type.equals("book")){
                query += "where id_books=" + Integer.toString(document.id);
            }else if (type.equals("journal")){
                query += "where id_books=" + Integer.toString(document.id);
            }else if(type.equals("avmaterial")){
                query += "where id_books=" + Integer.toString(document.id);
            }

            if (resultSet.next()){
                return resultSet.getBoolean(7);
            }else{
                return false;
            }


        }catch (Exception e){
            System.out.println("Error in checking active doc " + e.toString());
            return false;
        }
    }

    public static boolean isDocumentActiveById(int id, String type){
        try {
            String query = "select * from documents where " + type + "=" + Integer.toString(id);
            resultSet = statement.executeQuery(query);
            resultSet.next();

            return false;

        }catch (Exception e){
            System.out.println("Error isDocumentActiveById " + e.toString());
            return false;
        }
    }

    public void UpdateUser(User user){

    }

    public void UpdateDocument(Document document){

    }

    public void DeleteUser(User user){

    }

    public void DeleteDocument(Document document){

    }

    public boolean hasDocument(Document document){
        return true;
    }

    public boolean hasUser(User user){
        return true;
    }

    public boolean CheckOutDocument(User user, Document document){
        return true;
    }

    public void ReturnDocument(User user, Document document){

    }

    public ArrayList<Document> CheckOverdueDocuments(){
        return new ArrayList<>();
    }

    public int getUserDebt(User user){
        return -1;
    }

    public boolean isCorrectAuthorization(String username, String pass){
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT phonenumber, password FROM users");
            while (resultSet.next()){
                if(resultSet.getString(1).equals(username) && resultSet.getString(2).equals(pass)){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            System.out.println("error: " + e.toString());
        }
        return false;
    }

    public User GetUserByLogin(String username){
        return new Patron();
    }


    public ArrayList<Book> getAllBooks() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        String query = "select * from books";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){

            books.add(new Book(resultSet.getInt(1), resultSet.getString(2), new ArrayList<String>(Arrays.asList(resultSet.getString(3).split(", "))), resultSet.getInt(7),
                    new ArrayList<String>(Arrays.asList(resultSet.getString(8).split(", "))), resultSet.getBoolean(10), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getInt(6), true));
        }

        return books;
    }

    public ArrayList<Journal> getAllJournals() throws SQLException{
        ArrayList<Journal> journals = new ArrayList<>();

        String query = "select * from journals";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            journals.add(new Journal(resultSet.getString(2), new ArrayList<String>(Arrays.asList(resultSet.getString(3).split(", "))),
                    resultSet.getInt(6), new ArrayList<String>(Arrays.asList(resultSet.getString(7).split(", "))),
                    resultSet.getBoolean(9), "didnt found", resultSet.getString(4), resultSet.getString(5)));
        }

        return journals;
    }

    public ArrayList<AVmaterial> getAllAVmaterials() throws SQLException{
        ArrayList<AVmaterial> avmaterials = new ArrayList<>();

        String query = "select * from av_materials";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            avmaterials.add(new AVmaterial(resultSet.getString(2), new ArrayList<String>(Arrays.asList(resultSet.getString(3).split(", "))),
                    resultSet.getInt(4), new ArrayList<String>(Arrays.asList(resultSet.getString(5).split(", "))),
                    resultSet.getBoolean(7)));
        }

        return avmaterials;
    }
}
