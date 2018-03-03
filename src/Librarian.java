import java.sql.PreparedStatement;
public class Librarian extends User {

    /**
     * common constructor
     */
    public Librarian(String name, String phoneNumber, String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    /**
     * empty constructor
     */
    public Librarian(){

    }

    public void CreateUserDB(){
        try {
            PreparedStatement preparedStatement;

            preparedStatement = Database.connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember, password, isLibrarian) values(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.phoneNumber);
            preparedStatement.setString(3, this.address);
            preparedStatement.setInt(4, 0);
            preparedStatement.setBoolean(5, false);
            preparedStatement.setString(6, this.password);
            preparedStatement.setBoolean(7, true);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            System.out.println("error: " + ex.toString());
        }
    }
}
