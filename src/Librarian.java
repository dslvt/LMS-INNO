import javax.xml.crypto.Data;
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
            statement.executeUpdate("INSERT INTO request SET id_user = " + patron.id + ", id_document = " + document.id + "message = 'You should return this book'");

        } catch (SQLException e) {
            System.out.println("Error in sendRequest: " + e.toString());
        }
    }

    public void sendOutstandingRequest(Document document) {
        try {
            ResultSet resultSet = Database.SelectFromDB("SELECT*FROM libtasks WHERE id_document = " + Integer.toString(document.id) + " and type = 'checkout'");
            Integer id_user;
            Integer id_document;
            boolean mark = true;
            while (resultSet.next()) {
                id_user = resultSet.getInt("id_user");
                id_document = resultSet.getInt("id_document");
                if (id_user != null && id_document != null) {
                    Database.ExecuteQuery("INSERT INTO request SET id_user = " + id_user + ", id_document = " + id_document + ", message = '" + RequestsText.removed_queue_en +"'");
                } else {
                    mark = false;
                }
            }
            if(mark) {
                Database.ExecuteQuery("DELETE FROM libtasks WHERE id_document = " + Integer.toString(document.id) + " and type = 'checkout'");

                resultSet = Database.SelectFromDB("SELECT*FROM booking WHERE document_id = " + Integer.toString(document.id));
                while (resultSet.next()) {
                    Database.ExecuteQuery("INSERT INTO request SET id_user = " + resultSet.getInt("user_id") + ", id_document = " + resultSet.getInt("document_id") + ", message = '"+RequestsText.return_book_en+"'");
                }
            }

            ArrayList<Integer> documentIds = new ArrayList<>();
            ResultSet rs = Database.SelectFromDB("select * from documents where id_" + document.type.toString() + "s = " + Integer.toString(document.localId));
            while (rs.next()){
                documentIds.add(rs.getInt("id"));
            }

            Statement st = Database.connection.createStatement();

            java.util.Date date = new java.util.Date();
            if(CurrentSession.setDate != 0L)
                date.setTime(CurrentSession.setDate);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

            for (int i = 0; i < documentIds.size(); i++) {
                st.executeUpdate("UPDATE booking set returnTime = '" + timestamp + "', is_renew = '" + 1 + "' WHERE document_id = '" + documentIds.get(i) + "'");
            }

        } catch (Exception e) {
            System.out.println("Error in sendOutstandingRequest: " + e.toString());
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

}
