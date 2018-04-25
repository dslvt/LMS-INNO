import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Logging {

    public static void CreateLog(String text, int id){
        try{
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp timestamp1 = new java.sql.Timestamp(date.getTime());

            Database.ExecuteQuery("insert into logging set user_id = " + id + ", message = '" + text + "', time = '"+timestamp1+"';");
        }catch (Exception e){
            System.out.println("Error in Createlog:" + e.toString());
        }
    }

    public static ArrayList<String> getLast(int n){
        ArrayList<String> a = new ArrayList<>();
        try{
            ResultSet rs = Database.SelectFromDB("select * from logging order by id DESC;");
            for(int i = 0; i < n; i++){
                if (rs.next()){
                    int userId = rs.getInt("user_id");
                    java.util.Date date = new java.util.Date();
                    date = rs.getDate("time");
                    a.add(Integer.toString(userId) + "#"+ rs.getString("message") + "#" + date.toString());
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
