import javax.xml.crypto.Data;
import java.sql.PreparedStatement;

public abstract class User {
    public String name;
    public String phoneNumber;
    public String address;
    public int id;

    public static void CreateUser(User user){
        try {
            PreparedStatement preparedStatement;

            preparedStatement = Database.connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember) values(?, ?, ?, ?, ?)");
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
}