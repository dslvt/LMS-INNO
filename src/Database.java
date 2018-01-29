import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;
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
}
