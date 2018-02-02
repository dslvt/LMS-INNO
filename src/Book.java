import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Book extends Document{

    public String publisher, edition;
    public int publishYear;

    public Book (int id, String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference, String publisher, String edition, int publishYear,
                 boolean isActive){
        this.name = name;
        this.authors = authors;
        this.price = cost;
        this.keywords = keywords;
        this.isReference = isReference;
        this.publisher = publisher;
        this.edition = edition;
        this.publishYear = publishYear;
        this.location = location;
        this.isActive = isActive;

        type = DocumentType.book;
    }

    @Override
    public void CreateDocumentInDB() {
        Database db = new Database();
        Statement statement;
        ResultSet resultSet;
        PreparedStatement preparedStatement;

        try{
            Integer lastId = Database.isDocumentExist(this);
            if(lastId != -1){
                preparedStatement = Database.connection.prepareStatement("update books set number = number + 1 where id = " + lastId.toString());
                preparedStatement.executeUpdate();
            }else {
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
                if(resultSet.next()){
                    lastId = resultSet.getInt(1);
                }
            }

            preparedStatement = Database.connection.prepareStatement("insert into documents(id_books, location, type, isActive) values(?, ?, ?, ?)");
            preparedStatement.setInt(1, lastId);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, "books");
            preparedStatement.setBoolean(4, isActive);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error create book: " + e.toString());
        }
    }

}