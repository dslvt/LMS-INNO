import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Librarian extends User {
    public LibrarianType type;

    /**
     * common constructor
     */
    public Librarian(String name, String pass, String phoneNumber, String address, LibrarianType type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.type = type;
        this.password = pass;
    }


    /**
     * empty constructor
     */
    public Librarian() {

    }

    public void CreateUserDB(int idLibrarian) {
        try {
            if (Database.isAdmin(idLibrarian)) {
                Logging.CreateLog("create " + name, idLibrarian);
                PreparedStatement preparedStatement;

                preparedStatement = Database.connection.prepareStatement("INSERT INTO users(name, phoneNumber, address, debt, isFacultyMember, password, isLibrarian, type) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.phoneNumber);
                preparedStatement.setString(3, this.address);
                preparedStatement.setInt(4, 0);
                preparedStatement.setBoolean(5, false);
                preparedStatement.setString(6, this.password);
                preparedStatement.setBoolean(7, true);
                preparedStatement.setString(8, getParsedLibrarianType(type));
                preparedStatement.executeUpdate();

                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                int lastId = 0;
                if (resultSet.next()) {
                    lastId = resultSet.getInt(1);
                }
                this.id = lastId;
            }
        } catch (Exception ex) {
            System.out.println("Error create librarian: " + ex.toString());
        }
    }

    @Override
    public void ModifyUserDB(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, String type, boolean isLibrarian, int idLibrarian) {
        if (Database.isAdmin(idLibrarian)) {
            PreparedStatement preparedStatement;
            try {
                preparedStatement = Database.connection.prepareStatement("UPDATE users SET name = ?, phoneNumber = ?, address = ?, debt = ?, isFacultyMember = ?, password = ?, isLibrarian = ?, type = ? WHERE id = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.setString(3, address);
                preparedStatement.setInt(4, debt);
                preparedStatement.setBoolean(5, isFacultyMember);
                preparedStatement.setString(6, password);
                preparedStatement.setBoolean(7, isLibrarian);
                preparedStatement.setString(8, type);
                preparedStatement.setInt(9, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error in ModifyUserDB: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to modify user");
        }
    }

    @Override
    public int DeleteUserDB(int idLibrarian) {
        if (Database.isAdmin(idLibrarian)) {
            try {
                PreparedStatement ps = Database.connection.prepareStatement("DELETE FROM users WHERE id = ?");
                ps.setInt(1, this.id);
                ps.executeUpdate();
                return 0;
            } catch (Exception e) {
                System.out.println("Error in DeleteUSERdb " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete user");
        }
        return -1;
    }

    public void sendRequest(Document document, Patron patron) {
        try {
            Statement statement = Database.connection.createStatement();
            statement.executeUpdate("INSERT INTO request SET id_user = " + patron.id + ", id_document = " + document.id + ", message = 'You should return this book';");

        } catch (SQLException e) {
            System.out.println("Error in sendRequest: " + e.toString());
        }
    }

    public ArrayList<Patron> getDebtors() {
        ArrayList<Patron> debtors = new ArrayList<>();
        try {
            Statement statement = Database.connection.createStatement();
            statement.executeQuery("SELECT * FROM users WHERE debt != 0");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                debtors.add(Database.getPatronById(resultSet.getInt("id")));
            }
            return debtors;
        } catch (SQLException e) {
            System.out.println("Error in getDebtors: " + e.toString());
        }
        return debtors;
    }

    public ArrayList<Document> getOverdueDocuments() {
        ArrayList<Document> overdueDocuments = new ArrayList<>();
        try {
            Statement statement = Database.connection.createStatement();

            //Crete current date
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

            statement.executeQuery("SELECT * FROM booking");

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                java.util.Date bookingDate = new java.util.Date();
                bookingDate = resultSet.getDate("returnTime");
                if (!date.before(bookingDate)) {
                    overdueDocuments.add(Database.getDocumentById(resultSet.getInt("document_id")));
                }
            }

            return overdueDocuments;

        } catch (SQLException e) {
            System.out.println("Error in getOverdueDocuments: " + e.toString());
        }
        return overdueDocuments;
    }

    public ArrayList<LibTask> getQueue(Document document) {
        ArrayList<LibTask> libTasks = new ArrayList<>();
        try {
            ResultSet resultSet = Database.SelectFromDB("SELECT*FROM libtasks WHERE id_document = " + document.id + " and queue > -1");
            while (resultSet.next()) {
                int doc_id = resultSet.getInt("id_document");
                int user_id = resultSet.getInt("id_user");
                String taskType = resultSet.getString("type");
                int queue = resultSet.getInt("queue");
                LibTask libTask = new LibTask(Database.getDocumentById(doc_id), Database.getPatronById(user_id), taskType, true);
                libTask.id = resultSet.getInt("id");
                libTask.queue = queue;
                libTasks.add(libTask);
            }
        } catch (Exception e) {
            System.out.println("Error in getQueue: " + e.toString());
        }
        return libTasks;
    }

    public static String getParsedLibrarianType(LibrarianType pt) {
        String ans = "";
        switch (pt) {
            case Priv1:
                ans = "priv1";
                break;
            case Priv2:
                ans = "priv2";
                break;
            case Priv3:
                ans = "priv3";
                break;
            default:
                ans = "admin";
                break;
        }

        return ans;
    }

    public static LibrarianType getCorrectLibrarianType(String t) {
        LibrarianType librarianType = null;
        if (t.equals("priv1")) {
            librarianType = LibrarianType.Priv1;
        } else if (t.equals("priv2")) {
            librarianType = LibrarianType.Priv2;
        } else if (t.equals("priv3")) {
            librarianType = LibrarianType.Priv3;
        }
        else{
            librarianType = LibrarianType.admin;
        }

        return librarianType;
    }


}
