import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class Booking {

    Statement statement;
    //String userName = "root";
    //String password = "enaca2225";
    //String connectionUrl = "jdbc:mysql://localhost:3306/project?useSSL=false";

    /*
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Booking booking = new Booking();
        Document document = new AVmaterial();
        document.id = 15;
        User user = new Patron();
        user.id = 5;
        //booking.checkOut(document,user);
        //booking.renewBook(document);
        //booking.returnBook(document, user);
    }
    */

    /**
     * constructor
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Booking() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        //Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
        //statement = connection.createStatement();
        Database database = new Database();
        statement = database.connection.createStatement();
    }

    /**
     * checkOut document in database
     * @param user which check out doc
     */
    public void checkOut(Document document, User user) {
        try {

            //Crete new line in Booking
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            System.out.println(document.id);
            statement.executeUpdate("INSERT into booking set user_id = '" + user.id + "', document_id = '" + document.id + "', time = '" + timestamp + "' ");


            //Get line from Documents
            String type = getType(document);

            //Change number of available documents
            //statement.executeQuery("SELECT*FROM booking WHERE document_id = '" + document.id + "'");
            //ResultSet rec = statement.getResultSet();
            changeNumber(false, type, document);
        }catch (Exception e){
            System.out.println("Error in checkOut booking " + e.toString());
        }
    }

    /**
     * returning doc and delete row in booking table
     * @param document which we will return
     * @param user
     * @throws SQLException
     */
    public void returnBook(Document document, User user) throws SQLException {
        //Get line from Booking
        statement.executeQuery("SELECT*FROM booking WHERE document_id = '" + document.id + "'");

        //Current date
        java.util.Date date = new java.util.Date();

        //Get date of booking
        java.util.Date bookingDate = new java.util.Date();
        ResultSet rec = statement.getResultSet();
        if (rec.next()) {
            bookingDate = rec.getDate("time");
        }

        //Get type of document
        String type = getType(document);

        //Is user faculty member
        statement.executeQuery("SELECT*FROM users WHERE id = '" + user.id + "'");
        rec = statement.getResultSet();
        boolean isFaculty = false;
        if (rec.next()) {
            isFaculty = rec.getBoolean("isFacultyMember");
        }

        //Count the term of booking
        int term;
        if (type.equals("book")) {
            if (isFaculty) {
                term = 28;
            } else {
                statement.executeQuery("SELECT*FROM documents WHERE id = '" + document.id + "'");
                rec = statement.getResultSet();
                int id_books = 0;
                if (rec.next()) {
                    id_books = rec.getInt("id_books");
                }
                statement.executeQuery("SELECT*FROM books WHERE id = '" + id_books + "'");
                rec = statement.getResultSet();
                boolean isBestSeller = false;
                if (rec.next()) {
                    isBestSeller = rec.getBoolean("isBestSeller");
                }

                if (isBestSeller) {
                    term = 14;
                } else {
                    term = 21;
                }
            }
        } else {
            term = 14;
        }

        //Add term to date of booking
        Calendar c = Calendar.getInstance();
        c.setTime(bookingDate);
        c.add(Calendar.DATE, term);
        bookingDate = c.getTime();

        //Check overdue
        if (!date.before(bookingDate)) {
            int overdue = countOverdue(bookingDate, date, document);
            statement.executeUpdate("UPDATE users set debt = '"+overdue+"' WHERE id = '"+user.id+"'");
        }

        //Delete record from Booking
        statement.executeUpdate("DELETE FROM booking WHERE document_id = '" + document.id + "'");

        //Change number of available documents
        changeNumber(true, type, document);

    }

    /**
     * return document and add it back. Also we update time
     * @param document
     * @throws SQLException
     */
    public void renewBook(Document document) throws SQLException {
        //Get line from Booking
        statement.executeQuery("SELECT*FROM booking WHERE document_id = '" + document.id + "'");

        //Check can we renew book
        boolean isRenew = false;
        ResultSet rec = statement.getResultSet();
        if (rec.next()) {
            isRenew = rec.getBoolean("is_renew");
        }

        //Current date
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        //Renew document
        if(!isRenew){
            statement.executeUpdate("UPDATE booking set time = '"+timestamp+"', is_renew = '"+1+"' WHERE document_id = '"+document.id+"'");
        }

    }

    /**
     * @return type of document
     * @throws SQLException
     */
    private String getType(Document document) throws SQLException {
        statement.executeQuery("SELECT*FROM documents WHERE id = '" + document.id + "'");
        ResultSet line = statement.getResultSet();
        String type = "";
        if (line.next()) {
            type = line.getString("type");
        }
        return type;
    }

    /**
     * calculate how much user overdue to lib
     * @return debt
     * @throws SQLException
     */
    private int countOverdue(Date bookingDate, Date currentDate, Document document) throws SQLException {
        int days = (int) (bookingDate.getTime() - currentDate.getTime()/(1000 * 60 * 60 * 24));
        int overdue = days * 100;

        statement.executeQuery("SELECT*FROM documents WHERE id = '" + document.id + "'");
        ResultSet line = statement.getResultSet();

        String type = getType(document);

        if (type.equals("book")) {
            int id = 0;
            if (line.next()) {
                id = line.getInt("id_books");
            }
            statement.executeQuery("SELECT*FROM books WHERE id = '" + id + "'");
            line = statement.getResultSet();
        }
        if (type.equals("journal")){
            int id = 0;
            if (line.next()) {
                id = line.getInt("id_journals");
            }
            statement.executeQuery("SELECT*FROM journals WHERE id = '" + id + "'");
            line = statement.getResultSet();
        }
        if(type.equals("av_materials")){
            int id = 0;
            if (line.next()) {
                id = line.getInt("id_av_materials");
            }
            statement.executeQuery("SELECT*FROM av_materials WHERE id = '" + id + "'");
            line = statement.getResultSet();
        }
        int cost = 0;
        if (line.next()) {
            cost = line.getInt("cost");
        }

        if(overdue > cost){
            overdue = cost;
        }

        return overdue;
    }

    /**
     * change amount of current item
     * @throws SQLException
     */
    private void changeNumber(boolean add, String type, Document document) throws SQLException {
        int one;
        if (add) {
            one = 1;
        } else {
            one = -1;
        }
        statement.executeQuery("SELECT*FROM documents WHERE id = '" + document.id + "'");
        ResultSet rec = statement.getResultSet();
        //Change number of available documents
        if (type.equals("book")) {//If it's book
            int counter = 0;
            int id = 0;
            if (rec.next()) {
                id = rec.getInt("id_books");
                counter = rec.getInt("number") + one;
            }
            statement.executeQuery("SELECT*FROM books WHERE id = '" + id + "'");
            rec = statement.getResultSet();
            if (rec.next()) {
                counter = rec.getInt("number");
            }
            counter+=one;
            statement.executeUpdate("UPDATE books set numb = '" + counter + "' WHERE id ='" + id + "'");
        }
        if (type.equals("journal")) {//If it's journal
            int counter = 0;
            int id = 0;
            if (rec.next()) {
                id = rec.getInt("id_journals");
                counter = rec.getInt("number") + one;
            }
            statement.executeQuery("SELECT*FROM journals WHERE id = '" + id + "'");
            rec = statement.getResultSet();
            if (rec.next()) {
                counter = rec.getInt("number");
            }
            counter+=one;
            statement.executeUpdate("UPDATE journals set numb = '" + counter + "' WHERE id ='" + id + "'");
        }
        if (type.equals("av_materials")) {//If it's av material
            int counter = 0;
            int id = 0;
            if (rec.next()) {
                id = rec.getInt("id_av_materials");
            }
            statement.executeQuery("SELECT*FROM av_materials WHERE id = '" + id + "'");
            rec = statement.getResultSet();
            if (rec.next()) {
                counter = rec.getInt("number");
            }
            counter+=one;
            statement.executeUpdate("UPDATE av_materials set number = '" + counter + "' WHERE id ='" + id + "'");
        }
    }
}


