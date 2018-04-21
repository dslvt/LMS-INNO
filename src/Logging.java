import java.sql.ResultSet;
import java.util.ArrayList;

public class Logging {

    public static void CreateLog(String text, int id){
        try{
            Database.ExecuteQuery("insert into logging set user_id = " + id + ", message = '" + text + "';");
        }catch (Exception e){
            System.out.println("Error in Createlog:" + e.toString());
        }
    }

    public static ArrayList<String> getLast(int n){
        ArrayList<String> a = new ArrayList<>();
        try{
            ResultSet rs = Database.SelectFromDB("select * from logging order by id ASC|DESC;");
            for(int i = 0; i < n; i++){
                if (rs.next()){
                    int userId = rs.getInt("user_id");
                    a.add("user_id: " + Integer.toString(userId) + " message: "+ rs.getString("message"));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Error in getLast: " + e.toString());
        }
        return a;
    }
}
