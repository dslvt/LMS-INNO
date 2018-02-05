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

    /**
     * common constructor
     */
    public Database(){
        preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }catch (Exception ex){
            System.out.println("error creating db: " + ex.toString());
        }
    }

    /**
     * search document in database and return its existing
     * @return boolean
     */
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

    /**
     * check document's existing
     * @return id document
     * @throws SQLException
     */
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

    /**
     * our document took or not
     * @return boolean
     */
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

    /**
     * another implementation of isDocumentActive
     * @return boolean
     */
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

    /**
     * check correct authorization
     * @param username phoneNumber
     * @param pass password
     * @return if is correct or nor
     */
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

    /**
     * find patron by phone number
     * @param login
     * @return correct patron
     */
    public Patron getPatronByNumber(String login){
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from users where phoneNumber=" + login);
            resultSet.next();

            Patron patron = new Patron(resultSet.getString(2), resultSet.getString(7),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getBoolean(6), resultSet.getInt(5));

            patron.id = resultSet.getInt(1);

            return patron;
        }catch (Exception e){
            System.out.println("Error in getting user by login " + e.toString());
            return new Patron();
        }
    }

    /**
     * @return all books
     * @throws SQLException
     */
    public ArrayList<Book> getAllBooks() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        String query = "select * from books";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){

            books.add(new Book(resultSet.getString(2), new ArrayList<String>(Arrays.asList(resultSet.getString(3).split(", "))), resultSet.getInt(7),
                    new ArrayList<String>(Arrays.asList(resultSet.getString(8).split(", "))), resultSet.getBoolean(10), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getInt(6)));
        }

        return books;
    }

    /**
     * @return all journals in database
     * @throws SQLException
     */
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

    /**
     * @return all av materials
     * @throws SQLException
     */
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

    /**
     * @return all documents
     */
    public ArrayList<Document> getAllDocuments(){
        ArrayList<Document> users = new ArrayList<>();

        try {
            String query = "select * from documents";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);


            while (resultSet.next()){
                Document currentDoc = new AVmaterial();
                String findInCurrentDBQuery = "select * from ";
                Statement tconnection = connection.createStatement();

                if(resultSet.getInt(2) != 0){
                    findInCurrentDBQuery += "av_materials where id=" + Integer.toString(resultSet.getInt(2));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = new AVmaterial(res.getString("title"), new ArrayList<String>(Arrays.asList(res.getString("author").split(", "))),
                            res.getInt("cost"), new ArrayList<String>(Arrays.asList(res.getString("keywords").split(", "))),
                            res.getBoolean("reference"));
                    currentDoc.id = res.getInt(1);
                }else if(resultSet.getInt(3) != 0){
                    findInCurrentDBQuery += "books where id="+Integer.toString(resultSet.getInt(3));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = new Book(res.getString("title"), new ArrayList<String>(Arrays.asList(res.getString("author").split(", "))),
                            res.getInt("cost"), new ArrayList<String>(Arrays.asList(res.getString("keywords").split(", "))),
                            res.getBoolean("reference"), res.getString("publisher"), res.getString("edition"),
                            res.getInt("publish_year"));
                    currentDoc.id = res.getInt(1);
                }else if(resultSet.getInt(4) != 0){
                    findInCurrentDBQuery += "journals where id="+Integer.toString(resultSet.getInt(4));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = new Journal(res.getString("title"), new ArrayList<String>(Arrays.asList(res.getString("author").split(", "))),
                            res.getInt("cost"), new ArrayList<String>(Arrays.asList(res.getString("keywords").split(", "))),
                            res.getBoolean("reference"), "-1", res.getString("issue"), res.getString("editor"));
                    currentDoc.id = res.getInt(1);
                }
                users.add(currentDoc);
            }

        }catch (Exception e){
            System.out.println("Error in getAllDocuments " + e.toString());
        }

        return users;
    }

    /**
     * @return documents' id to correct finding document
     */
    public ArrayList<Integer> getAllDocumentsIDs(){
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select id from documents");
            while (resultSet.next()){
                ids.add(resultSet.getInt(1));
            }
        }catch (Exception e){
            System.out.println("Error getAllDocumentsIDs " + e.toString());
        }

        return ids;
    }
}
