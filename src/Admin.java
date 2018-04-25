import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin extends User {

    public Admin(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public void CreateUserDB(int idLibrarian) {
        try {
            //isExist
            Statement st = Database.connection.createStatement();
            ResultSet rs = st.executeQuery("select * from users where type = 'admin'");
            if(rs.next()){
                System.out.println("Admin already exist");
            }else {
                PreparedStatement preparedStatement;

                preparedStatement = Database.connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember, password, isLibrarian, type) values(?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.phoneNumber);
                preparedStatement.setString(3, this.address);
                preparedStatement.setInt(4, 0);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setString(6, this.password);
                preparedStatement.setBoolean(7, true);
                preparedStatement.setString(8, "admin");
                preparedStatement.executeUpdate();

                int globalID = 0;
                Statement statement  = Database.connection.createStatement();
                ResultSet res = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if (res.next()) {
                    globalID = res.getInt(1);
                }
                this.id = globalID;
            }
        } catch (Exception ex) {
            System.out.println("Error create admin: " + ex.toString());
        }
    }

    @Override
    public void ModifyUserDB(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, String type, boolean isLibrarian, int idLibrarian) {
        System.out.println("Error: you cannot modify admin");
    }

    @Override
    public int DeleteUserDB(int idLibrarian) {
        System.out.println("Error: you cannot delete admin");
        return 0;
    }
}
