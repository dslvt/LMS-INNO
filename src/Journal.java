import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                   boolean isReference, String publicationDate, String issue, String editor, boolean isActive, String location) {
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
        if (Database.isLibrarian(idLibrarian)) {
            Statement statement;
            ResultSet resultSet;
            PreparedStatement preparedStatement;

            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId == -1) {
                    preparedStatement = Database.connection.prepareStatement("insert into journals(title, author, issue, editor, cost, keywords, reference, number) values(?, ?, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, this.name);
                    preparedStatement.setString(2, this.authors.toString());
                    preparedStatement.setString(3, this.issue);
                    preparedStatement.setString(4, this.editor);
                    preparedStatement.setInt(5, this.price);
                    preparedStatement.setString(6, this.keywords.toString());
                    preparedStatement.setBoolean(7, this.isReference);
                    preparedStatement.setInt(8, 1);
                    preparedStatement.executeUpdate();

                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        lastId = resultSet.getInt(1);
                    }
                    this.localId = lastId;

                    preparedStatement = Database.connection.prepareStatement("insert into documents(id_journals, location, type) values(?, ?, ?)");
                    preparedStatement.setInt(1, lastId);
                    preparedStatement.setString(2, location);
                    preparedStatement.setString(3, "journals");
                    preparedStatement.executeUpdate();

                    int globalID = 0;
                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        globalID = resultSet.getInt(1);
                    }
                    this.id = globalID;
                }
                else{
                    System.out.println("Error create journal: this journal is already exist");
                }

            } catch (Exception e) {
                System.out.println("Error create journal: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new Journal");
        }
    }

    public ArrayList<Document> addCopies(int copies, int idLibrarian) {
        ArrayList<Document> newCopies = new ArrayList<>();
        if (Database.isLibrarian(idLibrarian)) {
            PreparedStatement preparedStatement;
            Statement statement;
            ResultSet resultSet;
            Integer lastId = Database.isDocumentExist(this);
            if (lastId != -1) {
                try {
                    for (int i = 0; i < copies; i++) {
                        preparedStatement = Database.connection.prepareStatement("update journals set number = number + 1 where id = " + lastId.toString());
                        preparedStatement.executeUpdate();

                        preparedStatement = Database.connection.prepareStatement("insert into documents(id_journals, location, type) values(?, ?, ?)");
                        preparedStatement.setInt(1, lastId);
                        preparedStatement.setString(2, location);
                        preparedStatement.setString(3, "journals");
                        preparedStatement.executeUpdate();

                        int globalID = 0;
                        statement = Database.connection.createStatement();
                        resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                        if (resultSet.next()) {
                            globalID = resultSet.getInt(1);
                        }

                        Journal newCopy = this;
                        newCopy.id = globalID;
                        newCopies.add(newCopy);
                    }
                } catch (SQLException e) {
                    System.out.println("Error add copies of  journal: " + e.toString());
                }
            } else {
                System.out.println("Error add copy of journal:  there is no journal with id " + lastId.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new copy of Journal");
        }

        return newCopies;
    }

    public void ModifyInDB(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords,
                           boolean isReference, String publicationDate, String issue, String editor, String location, int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            PreparedStatement preparedStatement;
            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    preparedStatement = Database.connection.prepareStatement("UPDATE journals SET title = ?, author = ?, issue = ?, editor = ?, cost = ?, keywords = ?, reference = ?, publicationDate = ? WHERE id = ?");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, authors.toString());
                    preparedStatement.setString(3, issue);
                    preparedStatement.setString(4, editor);
                    preparedStatement.setInt(5, cost);
                    preparedStatement.setString(6, keywords.toString());
                    preparedStatement.setBoolean(7, isReference);
                    preparedStatement.setString(8,publicationDate);
                    preparedStatement.setInt(9, this.localId);
                    preparedStatement.executeUpdate();

                    preparedStatement = Database.connection.prepareStatement("UPDATE documents SET location = ? WHERE id = ?");
                    preparedStatement.setString(1, location);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.executeUpdate();
                } else {
                    System.out.println("Error modify journal:  there is no journal with id " + lastId.toString());
                }

            } catch (SQLException e) {
                System.out.println("Error modify journal: " + e.toString());
            }

        } else {
            System.out.println("Error: User does not have access to modify Journal");
        }
    }

    @Override
    public void DeleteFromDB(int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            Statement statement;
            try {
                statement = Database.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    statement.executeUpdate("DELETE FROM journals WHERE id = " + lastId.toString());
                    statement.executeUpdate("DELETE FROM documents WHERE id = " + this.id);
                    statement.executeUpdate("DELETE FROM  journal_articles WHERE  journal_id = " + this.localId);

                } else {
                    System.out.println("Error delete journal:  there is no journal with id " + lastId.toString());
                }

            } catch (Exception e) {
                System.out.println("Error delete journal: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete Journal");
        }
    }

    public void deleteCopies(int copies, int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            Statement statement;
            try {
                statement = Database.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                statement.executeUpdate("DELETE FROM documents WHERE id_journals= " + lastId.toString() + " LIMIT " + copies);

                PreparedStatement preparedStatement;
                statement.executeQuery("SELECT number FROM journals WHERE id="+lastId);
                ResultSet resultSet = statement.getResultSet();
                resultSet.next();
                int number = resultSet.getInt("number");
                if(number>=copies) {
                    preparedStatement = Database.connection.prepareStatement("update journals set number = number -  "+ Integer.toString(copies)+" where id = " + lastId);
                    preparedStatement.executeUpdate();
                }
                else{
                    preparedStatement = Database.connection.prepareStatement("update journals set number = 0 where id = " + lastId);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("Error delete copy of journal: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete copies of Journal");
        }
    }

    @Override
    public boolean isCanBeTaken() {
        return !isReference && Database.getAmountOfCurrentJournal(this) > 0;
    }
}
