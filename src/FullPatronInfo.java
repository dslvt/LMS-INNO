import java.util.ArrayList;

public class FullPatronInfo {
    public static Pair<Patron, ArrayList<Document>> GetInfo(int patronId, int libId){
        Database db = new Database();
        Pair<Patron, ArrayList<Document>> ans = null;
        try{
            if(Database.isLibrarian(libId)){
                ans = new Pair<Patron, ArrayList<Document>>(db.getPatronById(patronId), db.getUserDocuments(db.getPatronById(patronId)));
            }
        }catch (Exception e){
            System.out.println("Error in fullPatronInfo: " + e.toString());
        }
        return ans;
    }
}
