import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Patron extends User {
    public boolean isFacultyMember;
    public int debt;

    /**
     * common constructor
     */
    public Patron(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt) {
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
    public Patron() {

    }

    public void CreateUserDB() {
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

            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
            int lastId = 0;
            if (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
            this.id = lastId;
        } catch (Exception ex) {
            System.out.println("Error create patron: " + ex.toString());
        }
    }


    public ArrayList<Document> getAllRequests(){
        ArrayList<Document> requests = new ArrayList<>();
        try {
            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_document from request WHERE id_user = " + this.id);
            while (resultSet.next()){
                requests.add(Database.getDocumentById(resultSet.getInt(1)));
            }

            return requests;

        } catch (SQLException e) {
            System.out.println("Error in getRequest: "+e.toString());
        }

        return requests;
    }

}



