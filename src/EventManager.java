import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class EventManager {

    private Statement statement;

    public EventManager() {
        try {
            statement = Database.connection.createStatement();
        } catch (Exception e) {
            System.out.println("EventManager: " + e.toString());
        }
    }

    public ArrayList<LibTask> GetElements(boolean withZeros) {
        ArrayList<LibTask> libTasks = new ArrayList<LibTask>();
        try {
            String query = "select * from libtasks order by id";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int doc_id = rs.getInt("id_document");
                int user_id = rs.getInt("id_user");
                String taskType = rs.getString("type");
                int queue = rs.getInt("queue");
                LibTask libTask = new LibTask(Database.getDocumentById(doc_id), Database.getPatronById(user_id), taskType, true);
                libTask.id = rs.getInt("id");
                libTask.queue = queue;
                if (queue < 0 || withZeros)
                    libTasks.add(libTask);
            }
        } catch (Exception e) {
            System.out.println("EventManager, GetElements: " + e.toString());
        }

        return libTasks;
    }

    public int CreateQuery(LibTask libTask) {
        int id = 0;
        try {
            if (!libTask.type.equals("checkout") || isCanCreateTask(libTask)) {
                PreparedStatement preparedStatement = Database.connection.prepareStatement("insert into libtasks(id_user, id_document, type, queue, unic_key) values(?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, libTask.user.id);
                preparedStatement.setInt(2, libTask.document.id);
                preparedStatement.setString(3, libTask.type);
                int pos;
                if (!libTask.type.equals("checkout")) {
                    pos = -1;
                } else {
                    pos = getCorrectPosInQueue(libTask);
                }
                preparedStatement.setInt(4, pos);
                preparedStatement.setString(5, libTask.unic_key);
                preparedStatement.executeUpdate();

                int globalID = 0;
                statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if (resultSet.next()) {
                    globalID = resultSet.getInt(1);
                }
                id = globalID;
            }
        } catch (Exception e) {
            System.out.println("EventManager, CreateQuery: " + e.toString());
        }
        return id;
    }

    public boolean isCanCreateTask(LibTask libTask) {
        boolean ans = false;
        try {
            ResultSet rs = Database.SelectFromDB("select * from libtasks where id_user=" + Integer.toString(libTask.user.id) + " and unic_key='" +
                    libTask.unic_key + "';");
            ans = !rs.next();
        } catch (Exception e) {
            System.out.println("EventManager, isCanCreateTask: " + e.toString());

        }
        return ans;
    }

    private int getCorrectPosInQueue(LibTask libTask) {
        int ans = -1;
        try {
            Patron patron = libTask.user;
            int amountOfDocuments = Database.getAmountOfCurrentDocument(libTask.document);
            int amountOfQuery = -1;
            ResultSet rs = Database.SelectFromDB("select * from libtasks where unic_key='" + Document.getUnicKey(libTask.document)
                    + "' and type='checkout' order by queue");
            ArrayList<LibTask> libTasks = new ArrayList<>();
            while (rs.next()) {
                libTasks.add(Database.getLibtaskByResultSet(rs));
                libTasks.get(libTasks.size() - 1).queue = rs.getInt("queue");
                libTasks.get(libTasks.size() - 1).unic_key = libTask.unic_key;
            }
            amountOfQuery = libTasks.size();
            if (amountOfDocuments > amountOfQuery) {
                if (libTasks.size() != 0) {
                    ans = libTasks.get(0).queue - 1;
                }
            } else {
                int locPos = libTasks.size();
                for (int i = 0; i < libTasks.size(); i++) {
                    if (Patron.isTypeBigger(patron.type, libTasks.get(i).user.type) > 0 && libTasks.get(i).queue > -1) {
                        locPos = i;
                        break;
                    }
                }

//                ans = libTasks.get(locPos).queue;
//                if (ans < 0){
//                    ans = 0;
//                    locPos++;
//                }
                if(locPos == libTasks.size()){
                    ans = libTasks.get(libTasks.size() - 1).queue + 1;
                }
                for (int i = locPos; i < libTasks.size(); i++) {
                    Database.ExecuteQuery("update libtasks set `queue` = `queue` + 1 where id = " + Integer.toString(libTasks.get(i).id));
                }
            }
        } catch (Exception e) {
            System.out.println("EventManager, getCorrectPosInQueue: " + e.toString());
        }
        return ans;
    }

    public void ExecuteQuery(LibTask libTask) {
        try {
            boolean isCanExecureQuery = false;
            Booking booking = new Booking();
            if (libTask.type.equals("checkout")) {
                int currentAmountOfDoc = Database.getAmountOfCurrentDocument(libTask.document);
                if(currentAmountOfDoc > 0) {
                    this.DeleteQuery(libTask);
                    booking.checkOut(libTask.document, libTask.user);
                    currentAmountOfDoc += -1;
                    int shift = currentAmountOfDoc - shiftOrderLeft(libTask.unic_key);
                    moveOrder(-shift, libTask.unic_key);
                    isCanExecureQuery = false;
                }
            } else if (libTask.type.equals("return")) {
                booking.returnBook(libTask.document, libTask.user);
                int currentAmountOfDoc = Database.getAmountOfCurrentDocument(libTask.document);
                int shift = currentAmountOfDoc - shiftOrderLeft(libTask.unic_key);
                moveOrder(-shift, libTask.unic_key);
                sentGetRequests(libTask.unic_key);
                isCanExecureQuery = true;
            } else if (libTask.type.equals("registration")){
                PreparedStatement preparedStatement = Database.connection.prepareStatement("UPDATE users SET isActive = ? WHERE id = ?");
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, libTask.user.id);
                preparedStatement.executeUpdate();
                isCanExecureQuery = true;
            } else if (libTask.type.equals("renew")){
                booking.renewBook(libTask.document, libTask.user);
                isCanExecureQuery = true;
            }
            if (isCanExecureQuery){
                this.DeleteQuery(libTask);
            }
        } catch (Exception e) {
            System.out.println("EventManager executeQuery: " + e.toString());
        }
    }

    public void checkRequest(){
        try{
            java.util.Date currentDay = new java.util.Date();

            ResultSet resultSet = Database.SelectFromDB("SELECT*FROM request WHERE time IS NOT NULL");
            while (resultSet.next()){
                java.util.Date returnDay = resultSet.getDate("time");
                Calendar day = Calendar.getInstance();
                day.setTime(returnDay);
                day.add(Calendar.DATE, 1);
                returnDay = day.getTime();
                if(currentDay.after(returnDay)){
                    ResultSet rs = Database.SelectFromDB("SELECT unic_key FROM libtasks WHERE id_document = " +resultSet.getInt("id_document")+ " and id_user = "+ resultSet.getInt("id_user"));
                    rs.next();

                    Database.ExecuteQuery("DELETE FROM libtasks WHERE id_user = "+ resultSet.getInt("id_user") + " and id_document = " +resultSet.getInt("id_document"));

                    int currentAmountOfDoc = Database.getAmountOfCurrentDocument(Database.getDocumentById(resultSet.getInt("id_document")));
                    int shift = currentAmountOfDoc - shiftOrderLeft(rs.getString("unic_key"));
                    moveOrder(-shift, rs.getString("unic_key"));

                    sentGetRequests(rs.getString("unic_key"));
                }
            }
        }catch (Exception e){
            System.out.println("Error in checkRequest: "+ e.toString());


        }
    }

    private void sentGetRequests(String libTaskUnicKey) {
        try {
            ResultSet rs = Database.SelectFromDB("select * from libtasks where unic_key = '" + libTaskUnicKey + "' and type = 'checkout' order by queue");
            while (rs.next()) {
                if (rs.getInt("queue") < 0) {
                    java.util.Date date = new java.util.Date();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                    Database.ExecuteQuery("INSERT INTO request SET id_user = " + rs.getInt("id_user") + ", id_document = " + rs.getInt("id_document") + ", message = '"+ RequestsText.get_book_en +"', time = '"+ timestamp + "';");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in sentGetRequests: " + e.toString());
        }
    }

    private int shiftOrderLeft(String unic) {
        ArrayList<Integer> ids = new ArrayList<>();
        int ans = -1;
        try {
            ResultSet rs = Database.SelectFromDB("select * from libtasks where unic_key = '" + unic + "' and queue < 0 and type = 'checkout' order by queue");
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
            for (int i = 0; i < ids.size(); i++) {
                Database.ExecuteQuery("update libtasks set `queue` = " + Integer.toString(-ids.size() + i) + " where id = " + Integer.toString(ids.get(i)));
            }
            ans = ids.size();

        } catch (Exception e) {
            System.out.println("EventManager shiftOrderLeft: " + e.toString());
        }
        return ans;
    }

    private void moveOrder(int a, String unic) {
        try {
            ResultSet rs = Database.SelectFromDB("select * from libtasks where unic_key = '" + unic + "' order by queue");
            while (rs.next()) {
                Database.ExecuteQuery("update libtasks set `queue` = `queue` + " + Integer.toString(a) + " where id = " +
                        Integer.toString(rs.getInt("id")));
            }
        } catch (Exception e) {
            System.out.println("EventManager moveOrder: " + e.toString());
        }
    }

    public void DeleteQuery(LibTask libTask) {
        try {
            PreparedStatement ps = Database.connection.prepareStatement("delete from libtasks where id = ?");
            ps.setInt(1, libTask.id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("EventManager deleteQuery: " + e.toString());
        }
    }
}
