import java.sql.PreparedStatement;

/**
 * Class for creating Admin of the system
 */

public class Admin extends User {

    /**
     * Constructor of current class Admin
     * @param name Name of user
     * @param phoneNumber Phone number which is used as login
     * @param address Address of user
     */

    public Admin(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Create user with type "admin" in Database
     */

    @Override
    public void CreateUserDB() {
        try {
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
        } catch (Exception ex) {
            System.out.println("Error create admin: " + ex.toString());
        }
    }

    /**
     * This method is not used, it is created because of inheritance
     */

    @Override
    public void ModifyUserDB(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, String type, boolean isLibrarian, int idLibrarian) {
        System.out.println("Error: you cannot modify admin");
    }

    /**
     * This method is not used, it is created because of inheritance
     */

    @Override
    public int DeleteUserDB(int idLibrarian) {
        System.out.println("Error: you cannot delete admin");
        return 0;
    }
}
