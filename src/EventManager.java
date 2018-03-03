import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class EventManager {

    private Database db;
    private Statement statement;

    public EventManager(){
        try {
            db = new Database();
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
                LibTask libTask = new LibTask(db.getDocumentById(doc_id), db.getPatronById(user_id), taskType);
                libTask.id = rs.getInt("id");
                libTasks.add(libTask);
            }
        }catch (Exception e){
            System.out.println("EventManager, GetElements: " + e.toString());
        }

        return libTasks;
    }

    public void CreateQuery(LibTask libTask){
        try{
            PreparedStatement preparedStatement = Database.connection.prepareStatement("insert into libtasks(id_user, id_document, type) values(?, ?, ?)");
            preparedStatement.setInt(1, libTask.user.id);
            preparedStatement.setInt(2, libTask.document.id);
            preparedStatement.setString(3, libTask.type);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("EventManager, CreateQuery: " + e.toString());
        }
    }

    public void ExecuteQuery(LibTask libTask){
        try {
            Booking booking = new Booking();
            if (libTask.type.equals("checkout")) {
                booking.checkOut(libTask.document, libTask.user);
            } else if (libTask.type.equals("return")) {
                booking.returnBook(libTask.document, libTask.user);
            }
            this.DeleteQuery(libTask);
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
