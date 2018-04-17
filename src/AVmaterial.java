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

        type = DocumentType.av_material;
    }

    /**
     * creating avmaterial in database
     */
    @Override
    public void CreateDocumentInDB(int idLibrarian) {
        if (Database.isLibrarianPriv2(idLibrarian)) {
            Statement statement;
            ResultSet resultSet;
            PreparedStatement preparedStatement;

            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId == -1) {
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

                    preparedStatement = Database.connection.prepareStatement("insert into documents(id_av_materials, location, type, isActive) values(?, ?, ?, ?)");
                    preparedStatement.setInt(1, lastId);
                    preparedStatement.setString(2, location);
                    preparedStatement.setString(3, "av_materials");
                    preparedStatement.setBoolean(4, this.isActive);
                    preparedStatement.executeUpdate();

                    int globalID = 0;
                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        globalID = resultSet.getInt(1);
                    }
                    this.id = globalID;
                }
                else {
                    System.out.println("Error create AV Material: this AV material is already exist");
                }

            } catch (Exception e) {
                System.out.println("Error create av_material: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new AV Material");
        }
    }

    public ArrayList<Document> addCopies(int copies, int idLibrarian) {
        ArrayList<Document> newCopies = new ArrayList<>();
        if (Database.isLibrarianPriv2(idLibrarian)) {
            PreparedStatement preparedStatement;
            Statement statement;
            ResultSet resultSet;
            Integer lastId = Database.isDocumentExist(this);
            if (lastId != -1) {
                try {
                    for (int i = 0; i < copies; i++) {
                        preparedStatement = Database.connection.prepareStatement("update av_materials set number = number + 1 where id = " + lastId);
                        preparedStatement.executeUpdate();
                        preparedStatement = Database.connection.prepareStatement("insert into documents(id_av_materials, location, type, isActive) values(?, ?, ?, ?)");
                        preparedStatement.setInt(1, lastId);
                        preparedStatement.setString(2, location);
                        preparedStatement.setString(3, "av_materials");
                        preparedStatement.setBoolean(4, this.isActive);
                        preparedStatement.executeUpdate();
                        int globalID = 0;
                        statement = Database.connection.createStatement();
                        resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                        if (resultSet.next()) {
                            globalID = resultSet.getInt(1);
                        }
                        AVmaterial newCopy = this;
                        newCopy.id = globalID;
                        newCopies.add(newCopy);
                    }
                } catch (SQLException e) {
                    System.out.println("Error add copies of  AV material: " + e.toString());
                }
            } else {
                System.out.println("Error add copy of AV material:  there is no AV material with id " + lastId.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new copy of AV Material");
        }

        return newCopies;
    }

    public void ModifyInDB(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String location, int idLibrarian) {
        if (Database.isLibrarianPriv1(idLibrarian)) {
            PreparedStatement preparedStatement;
            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    preparedStatement = Database.connection.prepareStatement("UPDATE av_materials SET title = ?, author = ?, cost = ?, keywords = ?, reference = ? WHERE id = ?");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, authors.toString());
                    preparedStatement.setInt(3, cost);
                    preparedStatement.setString(4, keywords.toString());
                    preparedStatement.setBoolean(5, isReference);
                    preparedStatement.setInt(6,this.localId);
                    preparedStatement.executeUpdate();

                    preparedStatement = Database.connection.prepareStatement("UPDATE documents SET location = ? WHERE id = ?");
                    preparedStatement.setString(1, location);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.executeUpdate();
                } else {
                    System.out.println("Error modify AV material: there is no AV material with id " + lastId.toString());
                }

            } catch (SQLException e) {
                System.out.println("Error modify AV material: " + e.toString());
            }

        } else {
            System.out.println("Error: User does not have access to modify AV Material");
        }
    }

    @Override
    public void DeleteFromDB(int idLibrarian) {
        if (Database.isLibrarianPriv3(idLibrarian)) {
            Statement statement;
            try {
                statement = Database.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    statement.executeUpdate("DELETE FROM av_materials WHERE id = " + lastId.toString());
                    statement.executeUpdate("DELETE FROM documents WHERE id_journals = " + lastId.toString());

                } else {
                    System.out.println("Error delete av material:  there is no av_material with id " + lastId.toString());
                }

            } catch (Exception e) {
                System.out.println("Error delete av_material: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete AV Material");
        }

    }
    public void deleteCopies(int copies, int idLibrarian) {
        if (Database.isLibrarianPriv3(idLibrarian)) {
            Statement statement;
            try {
                statement = Database.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                statement.executeUpdate("DELETE FROM documents WHERE id_av_materials= " + lastId.toString() + " LIMIT " + copies);

                PreparedStatement preparedStatement;
                statement.executeQuery("SELECT number FROM av_materials WHERE id="+lastId);
                ResultSet resultSet = statement.getResultSet();
                resultSet.next();
                int number = resultSet.getInt("number");
                if(number>=copies) {
                    preparedStatement = Database.connection.prepareStatement("update av_materials set number = number - "+ Integer.toString(copies)+" where id = " + lastId);
                    preparedStatement.executeUpdate();
                }
                else{
                    preparedStatement = Database.connection.prepareStatement("update av_materials set number = 0 where id = " + lastId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("Error delete copy of AV Material: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete copies of AV Material");
        }
    }

    @Override
    public boolean isCanBeTaken() {

        return !isReference && Database.getAmountOfCurrentAvmaterial(this) > 0;
    }
}
