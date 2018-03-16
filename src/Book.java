import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Book extends Document {

    public String publisher, edition;
    public int publishYear;
    public boolean isBestseller;

    /**
     * common constructor
     */
    public Book(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publisher, String edition, int publishYear, boolean isBestseller, String location, boolean isActive) {
        CreateDocument(name, cost, keywords, authors, isReference, isActive, location);
        this.publisher = publisher;
        this.edition = edition;
        this.publishYear = publishYear;
        this.isBestseller = isBestseller;

        type = DocumentType.book;
    }

    public Book() {
    }

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
                if (lastId == -1) {
                    preparedStatement = Database.connection.prepareStatement("insert into books(title, author, publisher, edition, publish_year, cost, keywords, reference, number, isBestSeller) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, this.name);
                    preparedStatement.setString(2, this.authors.toString());
                    preparedStatement.setString(3, publisher);
                    preparedStatement.setString(4, edition);
                    preparedStatement.setInt(5, publishYear);
                    preparedStatement.setInt(6, price);
                    preparedStatement.setString(7, keywords.toString());
                    preparedStatement.setBoolean(8, isReference);
                    preparedStatement.setInt(9, 1);
                    preparedStatement.setBoolean(10,this.isBestseller);
                    preparedStatement.executeUpdate();

                    statement = Database.connection.createStatement();
                    resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                    if (resultSet.next()) {
                        lastId = resultSet.getInt(1);
                    }
                    this.localId = lastId;


                    preparedStatement = Database.connection.prepareStatement("insert into documents(id_books, location, type, isActive) values(?, ?, ?, ?)");
                    preparedStatement.setInt(1, lastId);
                    preparedStatement.setString(2, location);
                    preparedStatement.setString(3, "books");
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
                else{
                    System.out.println("Error create book: this book is already exist");
                }

            } catch (Exception e) {
                System.out.println("Error create book: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new Book");
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
                        preparedStatement = Database.connection.prepareStatement("update books set number = number + 1 where id = " + lastId);
                        preparedStatement.executeUpdate();
                        preparedStatement = Database.connection.prepareStatement("insert into documents(id_books, location, type, isActive) values(?, ?, ?, ?)");
                        preparedStatement.setInt(1, lastId);
                        preparedStatement.setString(2, location);
                        preparedStatement.setString(3, "books");
                        preparedStatement.setBoolean(4, this.isActive);
                        preparedStatement.executeUpdate();
                        int globalID = 0;
                        statement = Database.connection.createStatement();
                        resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                        if (resultSet.next()) {
                            globalID = resultSet.getInt(1);
                        }
                        Book newCopy = this;
                        newCopy.id = globalID;
                        newCopies.add(newCopy);
                    }
                } catch (SQLException e) {
                    System.out.println("Error add copies of  book: " + e.toString());
                }
            } else {
                System.out.println("Error add copy of book:  there is no book with id " + lastId.toString());
            }
        } else {
            System.out.println("Error: User does not have access to add new copy of Book");
        }

        return newCopies;
    }

    public void ModifyInDB(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publisher, String edition, int publishYear, boolean isBestseller, String location, int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            PreparedStatement preparedStatement;
            try {
                Integer lastId = Database.isDocumentExist(this);
                if (lastId != -1) {
                    preparedStatement = Database.connection.prepareStatement("UPDATE books SET title = ?, author = ?, publisher = ?, edition = ?, publish_year = ?, cost = ?, keywords = ?, reference = ?,isBestSeller = ? WHERE id = ?");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, authors.toString());
                    preparedStatement.setString(3, publisher);
                    preparedStatement.setString(4, edition);
                    preparedStatement.setInt(5, publishYear);
                    preparedStatement.setInt(6, cost);
                    preparedStatement.setString(7, keywords.toString());
                    preparedStatement.setBoolean(8, isReference);
                    preparedStatement.setBoolean(9, isBestseller);
                    preparedStatement.setInt(10, this.localId);
                    preparedStatement.executeUpdate();

                    preparedStatement = Database.connection.prepareStatement("UPDATE documents SET location = ? WHERE id = ?");
                    preparedStatement.setString(1, location);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.executeUpdate();
                } else {
                    System.out.println("Error modify book:  there is no book with id " + lastId.toString());
                }

            } catch (SQLException e) {
                System.out.println("Error modify book: " + e.toString());
            }

        } else {
            System.out.println("Error: User does not have access to modify Book");
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
                    statement.executeUpdate("DELETE FROM books WHERE id = " + lastId.toString());//error
                    statement.executeUpdate("DELETE FROM documents WHERE id_books= " + lastId.toString());//error

                } else {
                    System.out.println("Error delete book:  there is no book with id " + lastId.toString());
                }

            } catch (Exception e) {
                System.out.println("Error delete book: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete Book");
        }
    }

    public void deleteCopies(int copies, int idLibrarian) {
        if (Database.isLibrarian(idLibrarian)) {
            Statement statement;
            try {
                PreparedStatement preparedStatement;
                statement = Database.connection.createStatement();
                Integer lastId = Database.isDocumentExist(this);
                statement.executeUpdate("DELETE FROM documents WHERE id_books= " + lastId.toString() + " LIMIT " + copies);

                statement.executeQuery("SELECT number FROM books WHERE id="+lastId);
                ResultSet resultSet = statement.getResultSet();
                resultSet.next();
                int number = resultSet.getInt("number");
                if(number>=copies) {
                    preparedStatement = Database.connection.prepareStatement("update books set number = number - " + Integer.toString(copies) + " where id = " + lastId);
                    preparedStatement.executeUpdate();
                }
                else{
                    preparedStatement = Database.connection.prepareStatement("update books set number = 0 where id = " + lastId);
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                System.out.println("Error delete copy of book: " + e.toString());
            }
        } else {
            System.out.println("Error: User does not have access to delete copies of Book");
        }
    }


    @Override
    public boolean isCanBeTaken() {
        return Database.getAmountOfCurrentBook(this) > 0 && !isReference;
    }
}
