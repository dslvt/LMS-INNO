import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Patron extends User {
    public boolean isFacultyMember;
    public int debt;
    public PatronType type;

    /**
     * common constructor
     */
    public Patron(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, PatronType type, boolean isActive) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.debt = debt;
        this.isFacultyMember = isFacultyMember;
        this.type = type;
        this.isActive = isActive;
    }

    /**
     * empty constructor
     */
    public Patron() {

    }

    public void CreateUserDB(int idLibrarian) {
        try {
            Logging.CreateLog("created patron " + name, idLibrarian);

            PreparedStatement preparedStatement;
            preparedStatement = Database.connection.prepareStatement("insert into users(name, phoneNumber, address, debt, isFacultyMember, password, isLibrarian, type, isActive) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.phoneNumber);
            preparedStatement.setString(3, this.address);
            preparedStatement.setInt(4, 0);
            preparedStatement.setBoolean(5, this.isFacultyMember);
            preparedStatement.setString(6, this.password);
            preparedStatement.setBoolean(7, false);
            preparedStatement.setString(8, getParsedPatronType(type));
            preparedStatement.setBoolean(9, this.isActive);

            preparedStatement.executeUpdate();

            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
            int lastId = 0;
            if (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
            this.id = lastId;
        } catch (Exception ex) {
            System.out.println("Error create patron: " + ex.toString());
        }
    }

    public void ModifyUserDB(String name, String password, String phoneNumber, String address, boolean isFacultyMember, int debt, String type, boolean isLibrarian, int idLibrarian) {
        if (Database.isLibrarianPriv1(idLibrarian)) {
            PreparedStatement preparedStatement;
            try {
                preparedStatement = Database.connection.prepareStatement("UPDATE users SET name = ?, phoneNumber = ?, address = ?, debt = ?, isFacultyMember = ?, password = ?, type = ? WHERE id = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phoneNumber);
                preparedStatement.setString(3, address);
                preparedStatement.setInt(4, debt);
                preparedStatement.setBoolean(5, isFacultyMember);
                preparedStatement.setString(6, password);
                preparedStatement.setString(7, type);
                preparedStatement.setInt(8, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error in ModifyUserDB: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to modify user");
        }
    }

    public int DeleteUserDB(int idLibrarian){
        if(Database.isLibrarianPriv3(idLibrarian)) {
            if(Database.getUserDocuments(this).isEmpty()) {
                try {
                    PreparedStatement ps = Database.connection.prepareStatement("delete from users where id = ?");
                    ps.setInt(1, this.id);
                    ps.executeUpdate();
                    return 0;
                } catch (Exception e) {
                    System.out.println("Error in DeleteUSERdb " + e.toString());
                }
            }
        }
        else {
            System.out.println("Error: User does not have access to delete user");
        }
        return -1;
    }


    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> requests = new ArrayList<>();
        try {
            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from request WHERE id_user = " + this.id);
            while (resultSet.next()) {
                requests.add(new Request(Database.getDocumentById(resultSet.getInt("id_document")), resultSet.getString("message")));
            }

            return requests;

        } catch (SQLException e) {
            System.out.println("Error in getRequest: " + e.toString());
        }

        return requests;
    }

    public static PatronType getCorrectPatronType(String t) {
        PatronType patronType = null;
        if (t.equals("Students") || t.equals("Student") || t.equals("students") || t.equals("student")) {
            patronType = PatronType.student;
        } else if (t.equals("instructor") || t.equals("instructors") || t.equals("Instructor") || t.equals("Instructors")) {
            patronType = PatronType.instructor;
        } else if (t.equals("TA") || t.equals("ta") || t.equals("TAs") || t.equals("tas") || t.equals("Teacher Assistant")) {
            patronType = PatronType.ta;
        } else if (t.equals("professor") || t.equals("professors") || t.equals("Professor") || t.equals("Professors")) {
            patronType = PatronType.professor;
        } else if (t.equals("visitingProf") || t.equals("visitingProfs") || t.equals("VisitingProf") || t.equals("VisitingProfs") || t.equals(("Visiting Professor"))) {
            patronType = PatronType.visitingProf;
        } else {
            patronType = PatronType.lib;
        }

        return patronType;
    }

    public static String getParsedPatronType(PatronType pt) {
        String ans = "";
            switch (pt) {
                case student:
                    ans = "student";
                    break;
                case ta:
                    ans = "ta";
                    break;
                case professor:
                    ans = "professor";
                    break;
                case instructor:
                    ans = "instructor";
                    break;
                case visitingProf:
                    ans = "visitingProf";
                    break;
            default:
                ans = "lib";
                break;
        }

        return ans;
    }

    public static int isTypeBigger(PatronType a, PatronType b) {
        int at = parsePatronTypeToInt(a);
        int bt = parsePatronTypeToInt(b);
        if (at > bt)
            return 1;
        else if (at == bt)
            return 0;
        else
            return -1;
    }

    private static int parsePatronTypeToInt(PatronType patronType) {
        int ans = 0;
        switch (patronType) {
            case student:
                ans = 5;
                break;
            case instructor:
                ans = 4;
                break;
            case ta:
                ans = 3;
                break;
            case visitingProf:
                ans = 2;
                break;
            case professor:
                ans = 1;
                break;
            default:
                ans = -1;
                break;
        }
        return ans;
    }
}



