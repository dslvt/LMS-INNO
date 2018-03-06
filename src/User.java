import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class User {
    public String name;
    public String phoneNumber;
    public String address;
    public int id;
    public String password;

    /**
     * add new user in database
     */
    public abstract void CreateUserDB();

    public void ModifyUserDB(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, boolean isLibrarian, int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            PreparedStatement preparedStatement;
            try {
                preparedStatement = Database.connection.prepareStatement("UPDATE users SET name = ?, phoneNumber = ?, address = ?, debt = ?, isFacultyMember = ?, password = ?, isLibrarian = ? WHERE id = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.setString(3, address);
                preparedStatement.setInt(4, debt);
                preparedStatement.setBoolean(5, isFacultyMember);
                preparedStatement.setString(6, password);
                preparedStatement.setBoolean(7, isLibrarian);
                preparedStatement.setInt(8, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error in ModifyUserDB: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to modify user");
        }
    }

    public int DeleteUserDB(int idLibrarian){
        if(Database.isLibrarian(idLibrarian)) {
            Database db = new Database();
            if(db.getUserDocuments((Patron) this).isEmpty()) {
                try {
                    PreparedStatement ps = Database.connection.prepareStatement("delete from users where id = ?");
                    ps.setInt(1, this.id);
                    ps.executeUpdate();
                    return 0;
                } catch (Exception e) {
                    System.out.println("Error in DeleteUSERdb " + e.toString());
                }
            }
            else{
                return -1;
            }
        }
        else {
            System.out.println("Error: User does not have access to delete user");
        }
        return -1;
    }
}