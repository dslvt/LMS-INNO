import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class User {
    public String name;
    public String phoneNumber;
    public String address;
    public int id;
    public String password;
    public boolean isActive;

    /**
     * add new user in database
     */
    public abstract void CreateUserDB(int idLibrarian);

    public abstract void ModifyUserDB(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, String type, boolean isLibrarian, int idLibrarian);

    public abstract int DeleteUserDB(int idLibrarian);

    public ArrayList<Pair<Document, Integer>> getAllOverdueDocuments(int libId){
        ArrayList<Pair<Document, Integer>> ans = new ArrayList<>();
        try{
            if(Database.isLibrarianPriv1(libId)) {
                Booking booking = new Booking();
                ArrayList<Document> docs = Database.getUserDocuments((Patron) this);
                for (int i = 0; i < docs.size(); i++) {
                    int countOver = booking.countOverdue(docs.get(i));
                    if (countOver > 0) {
                        ans.add(new Pair<Document, Integer>(docs.get(i), countOver));
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error in getAllOverdueDocuments, user: " + e.toString());
        }
        return ans;
    }
}