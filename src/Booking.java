import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Booking {
    private Database database;
    public Booking(){
        database = new Database();

        try {
            PreparedStatement preparedStatement = database.connection.prepareStatement("insert into documents (id_journals, type, location) values(?, ?, ?)");
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "asdgash");
            preparedStatement.setString(3, "shhreertert");

            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("error: " + e.toString());

        }

    }
}
