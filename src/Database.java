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

            return resultSet.next() && resultSet.getBoolean(7);

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
     * @return all documents
     */
    public ArrayList<Document> getAllDocuments(){
        ArrayList<Document> users = new ArrayList<>();

        try {
            String query = "select * from documents order by id";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);


            while (resultSet.next()){
                Document currentDoc = null;
                String findInCurrentDBQuery = "select * from ";
                Statement tconnection = connection.createStatement();

                if(resultSet.getInt(2) != 0){
                    findInCurrentDBQuery += "av_materials where id=" + Integer.toString(resultSet.getInt(2));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = createAVMaterialByResultSet(res);
                    currentDoc.id = res.getInt(1);
                }else if(resultSet.getInt(3) != 0){
                    findInCurrentDBQuery += "books where id="+Integer.toString(resultSet.getInt(3));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = createBookByResultSet(res);
                    currentDoc.id = res.getInt(1);
                }else if(resultSet.getInt(4) != 0){
                    findInCurrentDBQuery += "journals where id="+Integer.toString(resultSet.getInt(4));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = createJournalByResultSet(res);
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
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id from documents order by id");
            while (resultSet.next()){
                ids.add(resultSet.getInt(1));
            }
        }catch (Exception e){
            System.out.println("Error getAllDocumentsIDs " + e.toString());
        }

        return ids;
    }

    public ArrayList<Document> getUserDocuments(Patron patron){
        ArrayList<Document> documents = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from booking order by id");

            while (resultSet.next()){
                if(resultSet.getInt("user_id") == patron.id){
                    Document document = null;

                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery("select * from documents where id=" + resultSet.getInt("document_id"));
                    rs.next();

                    String query = "select * from ";
                    if(rs.getInt("id_av_materials") != 0){
                        query += "av_materials where id=" + Integer.toString(rs.getInt("id_av_materials"));

                        Statement tst = connection.createStatement();
                        ResultSet trs = tst.executeQuery(query);
                        trs.next();

                        document = createAVMaterialByResultSet(trs);
                    }else if (rs.getInt("id_books") != 0){
                        query += "books where id=" + Integer.toString(rs.getInt("id_books"));

                        Statement tst = connection.createStatement();
                        ResultSet trs = tst.executeQuery(query);
                        trs.next();

                        document = createBookByResultSet(trs);
                    }else if(rs.getInt("id_journals") != 0){
                        query += "journals where id=" + Integer.toString(rs.getInt("id_journals"));

                        Statement tst = connection.createStatement();
                        ResultSet trs = tst.executeQuery(query);
                        trs.next();

                        document = createJournalByResultSet(trs);
                    }

                    documents.add(document);
                }
            }
        }catch (Exception e){
            System.out.println("Error in getAllUserDocuments: " + e.toString());
        }

        return documents;
    }

    private static AVmaterial createAVMaterialByResultSet(ResultSet rs){
        AVmaterial aVmaterial = null;
        try {
            aVmaterial = new AVmaterial(rs.getString("title"), new ArrayList<String>(Arrays.asList(rs.getString("author").split(", "))),
                    rs.getInt("cost"), new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", "))),
                    rs.getBoolean("reference"));
            aVmaterial.id = rs.getInt("id");
        }catch (Exception e){
            System.out.println("Error in createAVMaterialByResultSet: " + e.toString());
        }

        return aVmaterial;
    }

    private static Book createBookByResultSet(ResultSet rs){
        Book book = null;
        try {
            book = new Book(rs.getString("title"), new ArrayList<String>(Arrays.asList(rs.getString("author").split(", "))),
                    rs.getInt("cost"), new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", "))),
                    rs.getBoolean("reference"), rs.getString("publisher"), rs.getString("edition"),
                    rs.getInt("publish_year"), rs.getBoolean("isBestSeller"));
            book.id = rs.getInt("id");
        }catch (Exception e){
            System.out.println("Error in createBookByResultSet: " + e.toString());
        }

        return book;
    }

    private static Journal createJournalByResultSet(ResultSet rs){
        Journal journal = null;
        try {
            journal = new Journal(rs.getString("title"), new ArrayList<String>(Arrays.asList(rs.getString("author").split(", "))),
                    rs.getInt("cost"), new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", "))),
                    rs.getBoolean("reference"), "-1", rs.getString("issue"), rs.getString("editor"));
            journal.id = rs.getInt("id");
        }catch (Exception e){
            System.out.println("Error in createBookByResultSet: " + e.toString());
        }

        return journal;
    }

    public static int getAmountOfCurrentBook(Book book){
        int ans = -1;
        try {
            statement = connection.createStatement();
            String query = "select number from books where id ="+Integer.toString(getCorrectIdInLocalDatabase(book.id));
            resultSet = statement.executeQuery(query);
            resultSet.next();

            ans =  resultSet.getInt("number");

        }catch (Exception e){
            System.out.println("Error in getAmountOfCurrentBook: " + e.toString());
        }

        return ans;
    }

    public static int getAmountOfCurrentJournal(Journal journal){
        int ans = -1;
        try {
            statement = connection.createStatement();
            String query = "select number from journals where id ="+Integer.toString(getCorrectIdInLocalDatabase(journal.id));
            resultSet = statement.executeQuery(query);
            resultSet.next();

            ans =  resultSet.getInt("number");

        }catch (Exception e){
            System.out.println("Error in getAmountOfCurrentJournal: " + e.toString());
        }

        return ans;
    }

    public static int getAmountOfCurrentAvmaterial(AVmaterial aVmaterial){
        int ansv = -1;
        try {
            statement = connection.createStatement();
            String query = "select number from av_materials where id ="+Integer.toString(getCorrectIdInLocalDatabase(aVmaterial.id));
            resultSet = statement.executeQuery(query);
            resultSet.next();

            ansv =  resultSet.getInt("number");

        }catch (Exception e){
            System.out.println("Error in getAmountOfCurrentAvmaterial: " + e.toString());
        }

        return ansv;
    }

    private static int getCorrectIdInLocalDatabase(int documentId){
        int id = -1;

        try{
            statement = connection.createStatement();
            String query = "select * from documents where id=" + Integer.toString(documentId);
            resultSet = statement.executeQuery(query);
            resultSet.next();

            if(resultSet.getInt("id_av_materials")!=0){
                id = resultSet.getInt("id_av_materials");
            }else if(resultSet.getInt("id_books")!= 0){
                id = resultSet.getInt("id_books");
            }else if(resultSet.getInt("id_journals") != 0){
                id = resultSet.getInt("id_journals");
            }

        }catch (Exception e){
            System.out.println("Error in getCorrectIdInLocalDatabase: " + e.toString());
        }

        return id;
    }
}
