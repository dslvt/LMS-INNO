import java.sql.PreparedStatement;

public abstract class User {
    public String name;
    public String phoneNumber;
    public String address;
    public int id;
    public String password;

    public void CreateUserDB(){
        try {
            PreparedStatement preparedStatement;

            preparedStatement = Database.connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember, password) values(?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.phoneNumber);
            preparedStatement.setString(3, this.address);
            preparedStatement.setInt(4, 10);
            preparedStatement.setBoolean(5, true);
            preparedStatement.setString(6, this.password);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            System.out.println("error: " + ex.toString());
        }
    }
}