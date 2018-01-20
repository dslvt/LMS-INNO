import java.sql.*;

public class Database {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/mydbtest?user=admin?utoReconnect=true&useSSL=false";
    private static final String user = "admin";
    private static final String password = "FJ`;62LfOTVZoM2+;3Qo983_zq9iGix9S107pi6)|CzU2`rdVRZD7?5a65sM;|6'54FE\\w9t4Ph~=";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) {
        String query = "select * from users";

        preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Драйвер подключен");
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                String email = resultSet.getString(4);
                System.out.printf("id: %d, name: %s, age: %d, email: %s %n", id, name, age, email);
            }

            preparedStatement = connection.prepareStatement("insert into booking(user_id, book_id) values (?, ?)");
            preparedStatement.setInt(1, 4);
            preparedStatement.setInt(2, 10);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            System.out.println("error: " + ex.toString());
        }
    }
}
