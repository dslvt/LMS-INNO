import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Librarian extends User {

    /**
     * common constructor
     */
    public Librarian(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


    /**
     * empty constructor
     */
    public Librarian() {

    }

    public void CreateUserDB() {
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
        } catch (Exception ex) {
            System.out.println("Error create librarian: " + ex.toString());
        }
    }

    public void sendRequest(Document document, Patron patron) {
        try {
            Statement statement = Database.connection.createStatement();
            statement.executeUpdate("INSERT INTO request SET id_user = " + patron.id + ", id_document = " + document.id);

        } catch (SQLException e) {
            System.out.println("Error in sendRequest: " + e.toString());
        }
    }

    public ArrayList<Patron> getDebtors() {
        ArrayList<Patron> debtors = new ArrayList<>();
        try {
            Database db = new Database();
            Statement statement = Database.connection.createStatement();
            statement.executeUpdate("SELECT * FROM users WHERE debt != 0");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                debtors.add(db.getPatronById(resultSet.getInt("id")));
            }
            return debtors;
        } catch (SQLException e) {
            System.out.println("Error in getDebtors: " + e.toString());
        }
        return debtors;
    }

    private ArrayList<Document> getOverdueDocuments() {
        ArrayList<Document> overdueDocuments = new ArrayList<>();
        try {
            Statement statement = Database.connection.createStatement();
            Database db = new Database();

            //Crete current date
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

            statement.executeQuery("SELECT * FROM booking");

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                java.util.Date bookingDate = new java.util.Date();
                bookingDate = resultSet.getDate("returnTime");
                if(!date.before(bookingDate)){
                    overdueDocuments.add(db.getDocumentById(resultSet.getInt("document_id")));
                }
            }

            return overdueDocuments;

        } catch (SQLException e) {
            System.out.println("Error in getOverdueDocuments: " + e.toString());
        }
        return overdueDocuments;
    }

}
