import javax.print.Doc;
import javax.print.attribute.standard.DocumentName;
import java.net.StandardSocketOptions;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Database {

//    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydbtest?useSSL=false";
//    private static final String user = "admin";
//    private static final String password = "FJ`;62LfOTVZoM2+;3Qo983_zq9iGix9S107pi6)|CzU2`rdVRZD7?5a65sM;|6'54FE\\w9t4Ph~=";
    //private static final String password = "333999333tima";
    String user = "root";
    String password = "enaca2225";
    String url = "jdbc:mysql://localhost:3306/project?useSSL=false";
//    String password = "123123123Aa";
//    String url = "jdbc:mysql://localhost:3306/db?useSSL=false";

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
        int localId = -1;
        try {
            int isDocExist = -1;

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select id_av_materials, id_books, id_journals, id from documents");

            while (resultSet.next()){
                int id=0;
                if(resultSet.getInt("id_av_materials") != 0){
                    id = resultSet.getInt("id_av_materials");
                }else if(resultSet.getInt("id_books") != 0){
                    id = resultSet.getInt("id_books");
                }else if(resultSet.getInt("id_journals") != 0){
                    id = resultSet.getInt("id_journals");
                }
                if(document.localId == id){
                    localId = id;
                }

            }

        }catch (Exception ex){
            System.out.println("error isDocExist: " + ex.toString());
        }

        return localId;
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
    public static boolean isCorrectAuthorization(String username, String pass){
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
    public static Patron getPatronByNumber(String login){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from users where phoneNumber=" + login);
            rs.next();

            Patron patron = new Patron(rs.getString(2), rs.getString(7),
                    rs.getString(3), rs.getString(4), rs.getBoolean(6), rs.getInt(5),  Patron.getCorrectPatronType(rs.getString("type")));

            patron.id = rs.getInt("id");

            return patron;
        }catch (Exception e){
            System.out.println("Error in getting user by login " + e.toString());
            return new Patron();
        }
    }

    /**
     * @return all documents
     */
    public static ArrayList<Document> getAllDocuments(){
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
                    currentDoc = createAVMaterialByResultSet(res, resultSet.getInt("id"), resultSet.getString("location"));
                    currentDoc.localId = res.getInt("id");
                    currentDoc.type = DocumentType.avmaterial;
                }else if(resultSet.getInt(3) != 0){
                    findInCurrentDBQuery += "books where id="+Integer.toString(resultSet.getInt(3));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = createBookByResultSet(res, resultSet.getInt("id"), resultSet.getString("location"));
                    currentDoc.localId = res.getInt("id");
                    currentDoc.type = DocumentType.book;
                }else if(resultSet.getInt(4) != 0){
                    findInCurrentDBQuery += "journals where id="+Integer.toString(resultSet.getInt(4));
                    ResultSet res = tconnection.executeQuery(findInCurrentDBQuery);
                    res.next();
                    currentDoc = createJournalByResultSet(res, resultSet.getInt("id"), resultSet.getString("location"));
                    currentDoc.localId = res.getInt("id");
                    currentDoc.type = DocumentType.journal;
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
    public static ArrayList<Integer> getAllDocumentsIDs(){
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

    public static ArrayList<Document> getUserDocuments(Patron patron){
        ArrayList<Document> documents = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from booking order by id");

            while (resultSet.next()){
                if(resultSet.getInt("user_id") == patron.id){
                    Document document = null;

                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery("select * from documents where id=" + resultSet.getInt("document_id"));
                    if(rs.next()) {
                        String query = "select * from ";
                        if (rs.getInt("id_av_materials") != 0) {
                            query += "av_materials where id=" + Integer.toString(rs.getInt("id_av_materials"));

                            Statement tst = connection.createStatement();
                            ResultSet trs = tst.executeQuery(query);
                            trs.next();

                            document = createAVMaterialByResultSet(trs, rs.getInt("id"), rs.getString("location"));
                            document.localId = rs.getInt("id_av_materials");
                        } else if (rs.getInt("id_books") != 0) {
                            query += "books where id=" + Integer.toString(rs.getInt("id_books"));

                            Statement tst = connection.createStatement();
                            ResultSet trs = tst.executeQuery(query);
                            trs.next();

                            document = createBookByResultSet(trs, rs.getInt("id"), rs.getString("location"));
                            document.localId = rs.getInt("id_books");

                        } else if (rs.getInt("id_journals") != 0) {
                            query += "journals where id=" + Integer.toString(rs.getInt("id_journals"));

                            Statement tst = connection.createStatement();
                            ResultSet trs = tst.executeQuery(query);
                            trs.next();

                            document = createJournalByResultSet(trs, rs.getInt("id"), rs.getString("location"));
                            document.localId = rs.getInt("id_journals");
                        }

                        documents.add(document);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error in getAllUserDocuments: " + e.toString());
        }

        return documents;
    }

    private static AVmaterial createAVMaterialByResultSet(ResultSet rs, int id, String location){
        AVmaterial aVmaterial = null;
        try {
            aVmaterial = new AVmaterial(rs.getString("title"), new ArrayList<String>(Arrays.asList(rs.getString("author").split(", "))),
                    rs.getInt("cost"), new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", "))),
                    rs.getBoolean("reference"), true, location);
            aVmaterial.id = id;
        }catch (Exception e){
            System.out.println("Error in createAVMaterialByResultSet: " + e.toString());
        }

        return aVmaterial;
    }

    private static Book createBookByResultSet(ResultSet rs, int id, String location){
        Book book = null;
        try {
            book = new Book(rs.getString("title"), new ArrayList<String>(Arrays.asList(rs.getString("author").split(", "))),
                    rs.getInt("cost"), new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", "))),
                    rs.getBoolean("reference"), rs.getString("publisher"), rs.getString("edition"),
                    rs.getInt("publish_year"), rs.getBoolean("isBestSeller"), location, true);
            book.id = id;
        }catch (Exception e){
            System.out.println("Error in createBookByResultSet: " + e.toString());
        }

        return book;
    }

    private static Journal createJournalByResultSet(ResultSet rs, int id, String location){
        Journal journal = null;
        try {
            journal = new Journal(rs.getString("title"), new ArrayList<String>(Arrays.asList(rs.getString("author").split(", "))),
                    rs.getInt("cost"), new ArrayList<String>(Arrays.asList(rs.getString("keywords").split(", "))),
                    rs.getBoolean("reference"), "-1", rs.getString("issue"), rs.getString("editor"),true, location);
            journal.id = id;
        }catch (Exception e){
            System.out.println("Error in createBookByResultSet: " + e.toString());
        }

        return journal;
    }
    public static int getAmountOfCurrentDocument(Document document){
        int ans = -1;
        try{
            Statement st = connection.createStatement();
            String type = Document.getParsedType(document.type);
            String query = "select number from " + type + " where id ="+Integer.toString(document.localId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            ans = rs.getInt("number");

        }catch (Exception e){
            System.out.println("Error in getAmountOfCurrentDocument: " + e.toString());
        }

        return ans;
    }

    public static int getAmountOfCurrentBook(Book book){
        int ans = -1;
        try {
            Statement st  = connection.createStatement();
            String query = "select number from books where id ="+Integer.toString(book.localId);
            ResultSet rs = st.executeQuery(query);
            rs.next();

            ans =  rs.getInt("number");

        }catch (Exception e){
            System.out.println("Error in getAmountOfCurrentBook: " + e.toString());
        }

        return ans;
    }

    public static int getAmountOfCurrentJournal(Journal journal){
        int ans = -1;
        try {
            Statement st = connection.createStatement();
            String query = "select number from journals where id ="+Integer.toString(getCorrectIdInLocalDatabase(journal.id));
            ResultSet rs = st.executeQuery(query);
            rs.next();

            ans =  rs.getInt("number");

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

    public static Document getDocumentById(int id){
        String query = "select * from documents where id=" + Integer.toString(id);
        Document document = null;
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int localId = -1;

            Statement localStatment = connection.createStatement();
            ResultSet localResultSet;

            if(resultSet.getInt("id_av_materials") != 0){
                String localQuery = "select * from av_materials where id="+Integer.toString(resultSet.getInt("id_av_materials"));

                localResultSet = localStatment.executeQuery(localQuery);
                localResultSet.next();

                document = (AVmaterial)createAVMaterialByResultSet(localResultSet, resultSet.getInt("id"), resultSet.getString("location"));
                document.localId = resultSet.getInt("id_av_materials");
            }else if(resultSet.getInt("id_books") != 0){
                String localQuery = "select * from books where id="+Integer.toString(resultSet.getInt("id_books"));

                localResultSet = localStatment.executeQuery(localQuery);
                localResultSet.next();

                document = (Book)createBookByResultSet(localResultSet, resultSet.getInt("id"), resultSet.getString("location"));
                document.localId = resultSet.getInt("id_books");
            }else if(resultSet.getInt("id_journals") != 0){
                String localQuery = "select * from journals where id="+Integer.toString(resultSet.getInt("id_journals"));

                localResultSet = localStatment.executeQuery(localQuery);
                localResultSet.next();

                document = (Journal)createJournalByResultSet(localResultSet, resultSet.getInt("id"), resultSet.getString("location"));
                document.localId = resultSet.getInt("id_journals");
            }

        }catch (Exception e){
            System.out.println("Error in getDocumentById: " + e.toString());
        }

        return document;
    }

    public static int getCorrectIdInLocalDatabase(int documentId){
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

    public static Patron getPatronById(int id){
        String query = "select * from users where id="+Integer.toString(id);
        Patron user = null;
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {

                user = new Patron(rs.getString("name"), rs.getString("password"), rs.getString("phoneNumber"),
                        rs.getString("address"), rs.getBoolean("isFacultyMember"), rs.getInt("debt"), Patron.getCorrectPatronType(rs.getString("type")));
                user.id = id;
            }
        }catch (Exception e){
            System.out.println("Database get_user_by_id: " + e.toString());
        }

        return user;
    }

    public static boolean isLibrarian(int id){
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users WHERE id =" + id);
            resultSet.next();
            boolean isLibrarian = resultSet.getBoolean("isLibrarian");

            return isLibrarian;

        } catch (SQLException e) {
            System.out.println("Error is in isLibrarian method: " + e.toString());
        }
        return false;
    }

    public static ArrayList<LibTask> getAllLibTasks(){
        String query = "select * from libtasks order by id";
        ArrayList<LibTask> ans = new ArrayList<>();
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int userid = resultSet.getInt("id_user");
                String libType = resultSet.getString("type");
                ans.add(new LibTask(getDocumentById(resultSet.getInt("id_document")), getPatronById(userid), libType));
            }
        }catch (Exception e){
            System.out.println("Error in database, getAllLibTasks: " + e.toString());
        }

        return ans;
    }

    public static LibTask getLibtaskByResultSet(ResultSet rs){
        LibTask libTask = null;
        try{
            int userid = rs.getInt("id_user");
            String libType = rs.getString("type");
            int id = rs.getInt("id");

            libTask = new LibTask(getDocumentById(rs.getInt("id_document")), getPatronById(userid), libType);
            libTask.id = id;
        }catch (Exception e){
            System.out.println("Error in database, getLibtaskByResultSet: " + e.toString());
        }
        return libTask;
    }

    public static boolean isRequestDocument(Document document){
        try {
            resultSet = statement.executeQuery("SELECT Count(*) AS total FROM request WHERE id_document ="+document.id);
            resultSet.next();
            if(resultSet.getInt("total")>0){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error in isRequestDocument: "+ e.toString());
        }
        return false;
    }

    public static ArrayList<Pair<Document, Integer>> getAllDocumentsWithoutCopies(){
        ArrayList<Pair<Document, Integer>> documents = new ArrayList<>();
        try{
            String query = "select * from books order by id";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Book book = createBookByResultSet(rs, -1, "Its dont important");
                book.localId = rs.getInt("id");
                documents.add(new Pair<Document, Integer>(book, rs.getInt("number")));
            }
            query = "select * from journals order by id";
            rs = st.executeQuery(query);
            while (rs.next()){
                Journal journal = createJournalByResultSet(rs, -1, "Its dont important");
                journal.localId = rs.getInt("id");
                documents.add(new Pair<Document, Integer>(journal, rs.getInt("number")));
            }
            query = "select * from av_materials order by id";
            rs = st.executeQuery(query);
            while (rs.next()){
                AVmaterial aVmaterial = createAVMaterialByResultSet(rs, -1, "Its dont important");
                aVmaterial.localId = rs.getInt("id");
                documents.add(new Pair<Document, Integer>(aVmaterial, rs.getInt("number")));
            }
        }catch (Exception e){
            System.out.println("Error in database, getAllDocumentsWithoutCopies: " + e.toString());
        }

        return documents;
    }

    public static ArrayList<UserRequest> getAllRequests(){
        ArrayList<UserRequest> requests = new ArrayList<>();
        try{
            String query = "select * from request order by id";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                int docId = rs.getInt("id_document");
                requests.add(new UserRequest(getPatronById(rs.getInt("id_user")), getDocumentById(docId)));
            }
        }catch (Exception e){
            System.out.println("Error in database, getAllRequests: " + e.toString());
        }

        return requests;
    }

    public static ArrayList<Pair<Document, Patron>> getAllDocumentsWithUsers(){
        ArrayList<Pair<Document, Patron>> ans = new ArrayList<>();
        try{
            String query = "select * from booking order by id";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                ans.add(new Pair<Document, Patron>(getDocumentById(rs.getInt("document_id")), getPatronById(userId)));
            }
        }catch (Exception e){
            System.out.println("Error in database, getAllDocumentsWithUsers: " + e.toString());
        }

        return ans;
    }

    public static ArrayList<Patron> getAllPatrons(){
        ArrayList<Patron> ans = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select id from users where isLibrarian = 0");

            while (rs.next()){
                ans.add(getPatronById(rs.getInt("id")));
            }

        }catch (Exception e){
            System.out.println("Error in getAllPatrons. db: " + e.toString());
        }
        return ans;
    }

    public static String getDocumentReturnDate(Document document){
        String returnDate = "";
        try {
            Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT returnTime FROM booking WHERE document_id =" + document.id);
            while (rs.next()){
                returnDate = rs.getDate(1).toString();
            }

        } catch (SQLException e) {
            System.out.println("Error in getDocumentReturnDate: "+ e.toString());
        }
        return returnDate;
    }

    public static void DeleteAllInTable(String tableName){
        try{
            ArrayList<Integer> ids = getAllTableIds(tableName);
            Statement st = connection.createStatement();
            for (int i = 0; i < ids.size(); i++) {
                st.executeUpdate("DELETE FROM " + tableName + " where id = " + ids.get(i).toString());
            }
        }catch (Exception e){
            System.out.println("Error in db, deleteAllInTable: " + e.toString());
        }
    }

    public static ArrayList<Integer> getAllTableIds(String tableName){
        ArrayList<Integer> ans = new ArrayList<>();

        try{
            String query = "select id from " + tableName + " order by id";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                ans.add(rs.getInt("id"));
            }
        }catch (Exception e){
            System.out.println("Error in db, getAllTableIds: " + e.toString());
        }

        return ans;
    }

    public static void ExecuteQuery(String query){
        try{
            Statement st = connection.createStatement();
            st.executeUpdate(query);
        }catch (Exception e){
            System.out.println("Error in db, executeQuery: " + e.toString());

        }
    }

    public static ResultSet SelectFromDB(String query){
        ResultSet rs = null;
        try{
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        }catch (Exception e){
            System.out.println("Error in db, selectFromDB: " + e.toString());
        }
        return rs;
    }

    public static int getFirstDocumentWithLocID(Document document){
        int ans = -1;
        try{
            String type = "id_"+Document.getParsedType(document.type);
            String query = "select * from documents where " + type + "=" + Integer.toString(document.localId);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            ans = rs.getInt("id");
        }catch (Exception e){
            System.out.println("Error in db, getFirstDocumentWithLocID: " + e.toString());
        }
        return ans;
    }

    public static boolean hasQueue(Document document){
        boolean hasQueue = false;
        try {
            ResultSet resultSet = Database.SelectFromDB("SELECT*FROM libtasks WHERE id_document = "+ document.id);
            while (resultSet.next()){
                if (resultSet.getInt("queue") > -1){
                    hasQueue = true;
                    break;
                }
            }

        }catch (Exception e){
            System.out.println("Error in hasQueue: "+ e.toString());
        }
        return hasQueue;
    }

    public static boolean isCanRenew(Patron patron, Document document){
        boolean isCanRenew = false;
        try{
            //Get line from Booking
            statement.executeQuery("SELECT*FROM booking WHERE document_id = '" + document.id + "'");

            //Check can we renew book
            boolean isRenew = false;
            ResultSet rec = statement.getResultSet();
            if (rec.next()) {
                isRenew = rec.getBoolean("is_renew");
            }

            //Get line from Users
            String typeUser = "";
            statement.executeQuery("SELECT type FROM users WHERE id = "+ patron.id);
            rec = statement.getResultSet();
            while (rec.next()){
                typeUser = rec.getString("1");
            }
            if (!(isRenew && typeUser.equals("visitingProf") && !Database.hasQueue(document))){
                isCanRenew = true;
            }
            if(typeUser.equals("visitingProf")&& !Database.hasQueue(document)) {
                isCanRenew = true;
            }

        }catch (Exception e){
            System.out.println("Error in isCanRenew: "+ e.toString());
        }
        return isCanRenew;
    }

}
