import java.sql.PreparedStatement;
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

    public void DeleteUserDB(int idLibrarian){
        if(Database.isLibrarian(idLibrarian)) {
            try {
                Database db = new Database();
                PreparedStatement ps = Database.connection.prepareStatement("delete from users where id = ?");
                ps.setInt(1, this.id);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println("Error in DeleteUSERdb " + e.toString());
            }
        }
        else {
            System.out.println("Error: User does not have access to delete Book");
        }
    }
}