import java.util.ArrayList;

public class Tester {
    Database db;

    public Tester(){
        db = new Database();
    }

    public void test1(){
        try {
            int id = 1;
            Patron patron = (Patron) CurrentSession.user;
            Booking booking = new Booking();
            AVmaterial aVmaterial = new AVmaterial(id);
            booking.checkOut(aVmaterial , patron);

            ArrayList<Document> documents = db.getUserDocuments(patron);
            for (int i = 0; i < documents.size(); i++) {
                if(documents.get(i) == aVmaterial){
                    System.out.println("Test1 passed");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error in takebook " + e.toString());
        }
    }

    public void test2(){
        try{
            int id = 1;

        }
        catch (Exception e){
            System.out.println("Error in takebook " + e.toString());
        }
    }

    public void test3(){
        try{
            int id = 1;
            Patron patron = (Patron) CurrentSession.user;
            Booking booking = new Booking();
            AVmaterial aVmaterial = new AVmaterial(id);
            if(booking.checkOut(aVmaterial , patron) == 28){
                System.out.println("Sosai");
            }

        }
        catch (Exception e){
            System.out.println("Error in takebook " + e.toString());
        }
    }

    public void test4(){
        try{
            int id = 1;
            Patron patron = (Patron) CurrentSession.user;
            Booking booking = new Booking();
            AVmaterial aVmaterial = new AVmaterial(id);
            if(booking.checkOut(aVmaterial , patron) == 14){
                System.out.println("Sosai");
            }

        }
        catch (Exception e){
            System.out.println("Error in takebook " + e.toString());
        }
    }
}
