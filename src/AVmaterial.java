import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.util.ArrayList;

public class AVmaterial extends Document {

    /**
     * empty constructor with id
     *
     * @param id
     */
    public AVmaterial(int id) {
        this.id = id;
    }

    /**
     * empty constructor
     */
    public AVmaterial() {
    }

    /**
     * common constructor
     */
    public AVmaterial(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, boolean isActive, String location) {
        CreateDocument(name, cost, keywords, authors, isReference, isActive, location);

        type = DocumentType.avmaterial;
    }

    /**
     * creating avmaterial in database
     */
    @Override
    public void CreateDocumentInDB(int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            Statement statement;
            ResultSet resultSet;
            PreparedStatement preparedStatement;

            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    preparedStatement = Database.connection.prepareStatement("update av_materials set number = number + 1 where id = " + lastId.toString());
                    preparedStatement.executeUpdate();
                } else {
                    preparedStatement = Database.connection.prepareStatement("insert into av_materials(title, author, cost, keywords, number, reference) values(?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, this.name);
                    preparedStatement.setString(2, this.authors.toString());
                    preparedStatement.setInt(3, price);
                    preparedStatement.setString(4, this.keywords.toString());
                    preparedStatement.setInt(5, 1);
                    preparedStatement.setBoolean(6, this.isReference);
                    preparedStatement.executeUpdate();

                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        lastId = resultSet.getInt(1);
                    }
                    this.localId = lastId;
                }

                preparedStatement = Database.connection.prepareStatement("insert into documents(id_av_materials, location, type) values(?, ?, ?)");
                preparedStatement.setInt(1, lastId);
                preparedStatement.setString(2, location);
                preparedStatement.setString(3, "av_materials");
                preparedStatement.executeUpdate();

                int globalID = 0;
                statement = Database.connection.createStatement();
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if (resultSet.next()) {
                    globalID = resultSet.getInt(1);
                }
                this.id = globalID;

            } catch (Exception e) {
                System.out.println("Error create av_material: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new AV Material");
        }
    }
    public void ModifyInDB(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String location, boolean isActive, int idLibrarian){
        if(Database.isLibrarian(idLibrarian)) {
            AVmaterial modifiedAV = new AVmaterial(name, authors, cost, keywords, isReference, isActive, location);
            modifiedAV.id = this.id;
            modifiedAV.localId = this.localId;
            this.DeleteFromDB(false, idLibrarian);
            modifiedAV.CreateDocumentInDB(idLibrarian);
        }
        else {
            System.out.println("Error: User does not have access to modify AV Material");
        }
    }

    @Override
    public void DeleteFromDB(boolean withCopies, int idLibrarian) {
        if(Database.isLibrarian(idLibrarian)) {
            Database db = new Database();
            Statement statement;
            try {
                statement = db.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    statement.executeUpdate("DELETE FROM av_materials WHERE id = " + lastId.toString());
                    if (withCopies) {
                        statement.executeUpdate("DELETE FROM documents WHERE id = " + this.id);
                    }
                } else {
                    System.out.println("Error delete av material:  there is no av_material with id " + lastId.toString());
                }

            } catch (Exception e) {
                System.out.println("Error delete av_material: " + e.toString());
            }
        }
        else {
            System.out.println("Error: User does not have access to delete AV Material");
        }

    }

    @Override
    public boolean isCanBeTaken() {

        return !isReference && Database.getAmountOfCurrentAvmaterial(this) > 0;
    }
}
