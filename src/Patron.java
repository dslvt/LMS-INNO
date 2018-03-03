import java.sql.PreparedStatement;

public class Patron extends User {
    public boolean isFacultyMember;
    public int debt;

    /**
     * common constructor
     */
    public Patron(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.debt = debt;
        this.isFacultyMember = isFacultyMember;
    }

    /**
     * empty constructor
     */
    public Patron(){

    }
    public void CreateUserDB(){
        try {
            PreparedStatement preparedStatement;

            preparedStatement = Database.connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember, password, isLibrarian) values(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.phoneNumber);
            preparedStatement.setString(3, this.address);
            preparedStatement.setInt(4, 0);
            preparedStatement.setBoolean(5, this.isFacultyMember);
            preparedStatement.setString(6, this.password);
            preparedStatement.setBoolean(7, false);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            System.out.println("error: " + ex.toString());
        }
    }
}
