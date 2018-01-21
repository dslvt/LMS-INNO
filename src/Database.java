import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydbtest?user=admin?utoReconnect=true&useSSL=false";
    private static final String user = "admin";
    private static final String password = "FJ`;62LfOTVZoM2+;3Qo983_zq9iGix9S107pi6)|CzU2`rdVRZD7?5a65sM;|6'54FE\\w9t4Ph~=";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    public Database(){
        preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }catch (Exception ex){
            System.out.println("error: " + ex.toString());
        }
    }

    public boolean CreateDocument(Document document){
        return true;
    }

    public boolean CreateUser(User user){
        return true;
    }

    public boolean UpdateUser(User user){
        return true;
    }

    public boolean UpdateDocument(Document document){
        return true;
    }

    public boolean DeleteUser(User user){
        return true;
    }

    public boolean DeleteDocument(Document document){
        return true;
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

    public boolean ReturnDocument(User user, Document document){
        return true;
    }

    public ArrayList<Document> CheckOverdueDocuments(){
        return new ArrayList<>();
    }

    public int getUserDebt(User user){
        return -1;
    }

    public boolean isCorrectAuthorization(String username, String pass){
        return true;
    }

    public User GetUserByLogin(String username){
        return new Patron();
    }
}
