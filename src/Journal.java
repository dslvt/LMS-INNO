import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Journal extends Document {

    public String publicationDate;
    public String issue;
    public String editor;

    /**
     * common constructor
     */
    public Journal(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords,
                   boolean isReference, String publicationDate, String issue, String editor, boolean isActive, String location){
        CreateDocument(name, cost, keywords, authors, isReference, isActive, location);
        this.publicationDate = publicationDate;
        this.issue = issue;
        this.editor = editor;

        type = DocumentType.journal;
    }

    /**
     * creating new row in database
     */
    @Override
    public void CreateDocumentInDB(int idLibrarian) {
        if(Database.isLibrarian(idLibrarian)) {
            Statement statement;
            ResultSet resultSet;
            PreparedStatement preparedStatement;

            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    preparedStatement = Database.connection.prepareStatement("update books set number = number + 1 where id = " + lastId.toString());
                    preparedStatement.executeUpdate();
                } else {
                    preparedStatement = Database.connection.prepareStatement("insert into books(title, author, issue, editor, cost, keywords, reference) values(?, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, this.name);
                    preparedStatement.setString(2, this.authors.toString());
                    preparedStatement.setString(3, issue);
                    preparedStatement.setString(4, editor);
                    preparedStatement.setInt(5, price);
                    preparedStatement.setString(6, keywords.toString());
                    preparedStatement.setBoolean(7, isReference);
                    preparedStatement.executeUpdate();

                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        lastId = resultSet.getInt(1);
                    }
                    this.localId = lastId;
                }

                preparedStatement = Database.connection.prepareStatement("insert into documents(id_journals, location, type, isActive) values(?, ?, ?, ?)");
                preparedStatement.setInt(1, lastId);
                preparedStatement.setString(2, location);
                preparedStatement.setString(3, "journals");
                preparedStatement.setBoolean(4, isActive);
                preparedStatement.executeUpdate();

                int globalID = 0;
                statement = Database.connection.createStatement();
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if (resultSet.next()) {
                    globalID = resultSet.getInt(1);
                }
                this.id = globalID;

            } catch (Exception e) {
                System.out.println("Error create journal: " + e.toString());
            }
        }
        else {
            System.out.println("Error: User does not have access to add new Journal");
        }
    }

    public void ModifyInDB(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords,
                           boolean isReference, String publicationDate, String issue, String editor, boolean isActive, String location, int idLibrarian){
        if(Database.isLibrarian(idLibrarian)) {
            Journal modifiedJournal = new Journal(name, authors, cost, keywords, isReference, publicationDate, issue,editor,isActive,location);
            modifiedJournal.id = this.id;
            modifiedJournal.localId = this.localId;
            this.DeleteFromDB(false, idLibrarian);
            modifiedJournal.CreateDocumentInDB(idLibrarian);
        }
        else {
            System.out.println("Error: User does not have access to modify Journal");
        }
    }

    @Override
    public void DeleteFromDB(boolean withCopies, int idLibrarian){
        if(Database.isLibrarian(idLibrarian)) {
            Database db = new Database();
            Statement statement;
            try {
                statement = db.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    statement.executeUpdate("DELETE FROM journals WHERE id = " + lastId.toString());
                    if (withCopies) {
                        statement.executeUpdate("DELETE FROM documents WHERE id = " + this.id);
                    }
                } else {
                    System.out.println("Error delete journal:  there is no journal with id " + lastId.toString());
                }

            } catch (Exception e) {
                System.out.println("Error delete journal: " + e.toString());
            }
        }
        else {
            System.out.println("Error: User does not have access to delete Journal");
        }
    }

    @Override
    public boolean isCanBeTaken() {
        return !isReference && Database.getAmountOfCurrentJournal(this) > 0;
    }
}
