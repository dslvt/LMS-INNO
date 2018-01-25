import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydbtest?user=admin?utoReconnect=true&useSSL=false";
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
            System.out.println("error: " + ex.toString());
        }
    }

    public void CreateMaterial(Document doc){
        try {
            Integer lastId = isDocumentExist(doc);
            if(lastId != -1){
                preparedStatement = connection.prepareStatement("update av_materials set number = number + 1 where id = " + lastId.toString());
                preparedStatement.executeUpdate();
            }else {
                preparedStatement = connection.prepareStatement("insert into av_materials(title, author, cost, keywords, number, reference) values(?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, doc.name);
                preparedStatement.setString(2, doc.authors.toString());
                preparedStatement.setInt(3, 0);
                preparedStatement.setString(4, doc.keywords.toString());
                preparedStatement.setInt(5, 1);
                preparedStatement.setBoolean(6, false);
                preparedStatement.executeUpdate();

                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if(resultSet.next()){
                    lastId = resultSet.getInt(1);
                }
            }

            preparedStatement = connection.prepareStatement("insert into documents(id_av_materials, location, type) values(?, ?, ?)");
            preparedStatement.setInt(1, lastId);
            preparedStatement.setString(2, "2 row, 4 column");
            preparedStatement.setString(3, "av_materials");
            preparedStatement.executeUpdate();

        }catch (Exception e) {
            System.out.println("error: " + e.toString());
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
            System.out.println("error: " + ex.toString());
            return -1;
        }
    }

    public static void CreateUser(User user){
        try {
            preparedStatement = connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember) values(?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.name);
            preparedStatement.setString(2, user.phoneNumber);
            preparedStatement.setString(3, user.address);
            preparedStatement.setInt(4, 10);
            preparedStatement.setBoolean(5, true);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            System.out.println("error: " + ex.toString());
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
