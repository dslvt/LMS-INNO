import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class EventManager {

    private Statement statement;

    public EventManager(){
        try {
            statement = Database.connection.createStatement();
        }catch (Exception e){
            System.out.println("EventManager: " + e.toString());
        }
    }

    public ArrayList<LibTask> GetElements(){
        ArrayList<LibTask> libTasks = new ArrayList<LibTask>();
        try{
            String query = "select * from libtasks order by id";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                int doc_id = rs.getInt("id_document");
                int user_id = rs.getInt("id_user");
                String taskType = rs.getString("type");
                LibTask libTask = new LibTask(Database.getDocumentById(doc_id), Database.getPatronById(user_id), taskType);
                libTask.id = rs.getInt("id");
                libTasks.add(libTask);
            }
        }catch (Exception e){
            System.out.println("EventManager, GetElements: " + e.toString());
        }

        return libTasks;
    }

    public int CreateQuery(LibTask libTask){
        int id = 0;
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement("insert into libtasks(id_user, id_document, type, queue, unic_key) values(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, libTask.user.id);
            preparedStatement.setInt(2, libTask.document.id);
            preparedStatement.setString(3, libTask.type);
            preparedStatement.setInt(4, getCorrectPosInQueue(libTask));
            preparedStatement.setString(5, Document.getUnicKey(libTask.document));
            preparedStatement.executeUpdate();

            int globalID = 0;
            statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
            if (resultSet.next()) {
                globalID = resultSet.getInt(1);
            }
            id = globalID;
        }catch (Exception e){
            System.out.println("EventManager, CreateQuery: " + e.toString());
        }
        return id;
    }

    private int getCorrectPosInQueue(LibTask libTask){
        int ans = -1;
        try {
            Patron patron = (Patron) libTask.user;
            int amountOfDocuments = Database.getAmountOfCurrentDocument(libTask.document);
            int amountOfQuery = -1;
            ResultSet rs = Database.SelectFromDB("select * from libtasks where unic_key='" + Document.getUnicKey(libTask.document)
                    + "' and type='checkout' order by queue");
            ArrayList<LibTask> libTasks = new ArrayList<>();
            while(rs.next()){
                libTasks.add(Database.getLibtaskByResultSet(rs));
                libTasks.get(libTasks.size()-1).queue = rs.getInt("queue");
                libTasks.get(libTasks.size()-1).unic_key = libTask.unic_key;
            }
            amountOfQuery = libTasks.size();
            if(amountOfDocuments > amountOfQuery){
                if(libTasks.size() != 0){
                    ans = libTasks.get(0).queue - 1;
                }
            }else{
                int locPos = libTasks.size()-1;
                for (int i = 0; i < libTasks.size(); i++) {
                    if(Patron.isTypeBigger(patron.type, libTasks.get(i).user.type) < 0){
                        locPos = i;
                        break;
                    }
                }
                ans = libTasks.get(locPos).queue;
                for (int i = locPos; i < libTasks.size(); i++) {
                    Database.ExecuteQuery("update libtasks set `queue` = `queue` + 1 where id = " + Integer.toString(libTasks.get(i).id));
                }
            }
        }catch (Exception e){
            System.out.println("EventManager, getCorrectPosInQueue: " + e.toString());
        }
        return ans;
    }

    public void ExecuteQuery(LibTask libTask){
        try {
            Booking booking = new Booking();
            this.DeleteQuery(libTask);
            if (libTask.type.equals("checkout")) {
                booking.checkOut(libTask.document, libTask.user);
            } else if (libTask.type.equals("return")) {
                booking.returnBook(libTask.document, libTask.user);
            }
        }catch (Exception e){
            System.out.println("EventManager executeQuery: " + e.toString());
        }
    }

    public void DeleteQuery(LibTask libTask){
        try{
            Database db = new Database();
            PreparedStatement ps = db.connection.prepareStatement("delete from libtasks where id = ?");
            ps.setInt(1, libTask.id);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println("EventManager deleteQuery: " + e.toString());
        }
    }
}
