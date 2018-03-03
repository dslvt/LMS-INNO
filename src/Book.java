import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Book extends Document{

    public String publisher, edition;
    public int publishYear;
    public boolean isBestseller;

    /**
     *common constructor
     */
    public Book(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publisher, String edition, int publishYear, boolean isBestseller, String location, boolean isActive){
        CreateDocument(name, cost, keywords, authors, isReference, isActive, location);
        this.publisher = publisher;
        this.edition = edition;
        this.publishYear = publishYear;
        this.isBestseller = isBestseller;

        type = DocumentType.book;
    }

    public Book(){}
    /**
     * creating book in database
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
                    preparedStatement = Database.connection.prepareStatement("update books set number = number + 1 where id = " + lastId.toString());
                    preparedStatement.executeUpdate();
                } else {
                    preparedStatement = Database.connection.prepareStatement("insert into books(title, author, publisher, edition, publish_year, cost, keywords, reference, number) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, this.name);
                    preparedStatement.setString(2, this.authors.toString());
                    preparedStatement.setString(3, publisher);
                    preparedStatement.setString(4, edition);
                    preparedStatement.setInt(5, publishYear);
                    preparedStatement.setInt(6, price);
                    preparedStatement.setString(7, keywords.toString());
                    preparedStatement.setBoolean(8, isReference);
                    preparedStatement.setInt(9, 1);
                    preparedStatement.executeUpdate();

                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        lastId = resultSet.getInt(1);
                    }
                    this.localId = lastId;
                }

                preparedStatement = Database.connection.prepareStatement("insert into documents(id_books, location, type) values(?, ?, ?)");
                preparedStatement.setInt(1, lastId);
                preparedStatement.setString(2, location);
                preparedStatement.setString(3, "books");
                preparedStatement.executeUpdate();

                int globalID = 0;
                statement = Database.connection.createStatement();
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if (resultSet.next()) {
                    globalID = resultSet.getInt(1);
                }
                this.id = globalID;

            } catch (Exception e) {
                System.out.println("Error create book: " + e.toString());
            }
        }
        else{
            System.out.println("Error: User does not have access to add new Book");
        }
    }

    public void ModifyInDB(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publisher, String edition, int publishYear, boolean isBestseller, String location, boolean isActive, int idLibrarian){
        if(Database.isLibrarian(idLibrarian)) {
            Book modifiedBook = new Book(name, authors, cost, keywords, isReference, publisher, edition, publishYear, isBestseller, location, isActive);
            modifiedBook.id = this.id;
            modifiedBook.localId = this.localId;
            this.DeleteFromDB(false, idLibrarian);
            modifiedBook.CreateDocumentInDB(idLibrarian);
        }
        else {
            System.out.println("Error: User does not have access to modify Book");
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
                    statement.executeUpdate("DELETE FROM books WHERE id = " + lastId.toString());//error
                    if (withCopies) {
                        statement.executeUpdate("DELETE FROM documents WHERE id = " + this.id);//error
                    }
                } else {
                    System.out.println("Error delete book:  there is no book with id " + lastId.toString());
                }

            } catch (Exception e) {
                System.out.println("Error delete book: " + e.toString());
            }
        }
        else {
            System.out.println("Error: User does not have access to delete Book");
        }
    }


    @Override
    public boolean isCanBeTaken() {
        return Database.getAmountOfCurrentBook(this) > 0 && !isReference;
    }
}
