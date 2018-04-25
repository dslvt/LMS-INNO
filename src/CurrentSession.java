/**
 * Cookie-class for saving information of current user in system
 */

public class CurrentSession {
    public static User user;
    public static Patron editUser;
    public static Document editDocument;
    public static long setDate = 0L;

    /**
     * Check requests of users for necessity to renew them
     */

    public static void CurrentWork(){
        if(Database.isLibrarian(user.id)){
            EventManager em = new EventManager();
            em.checkRequest();
        }
    }
}
