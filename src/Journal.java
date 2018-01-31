import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Journal extends Document {

    public String publicationDate;
    public String issue;
    public String editor;

    public Journal(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords,
                   boolean isReference, String publicationDate, String issue, String editor, String location){
        this.name = name;
        this.authors = authors;
        this.price = cost;
        this.keywords = keywords;
        this.isReference = isReference;
        this.publicationDate = publicationDate;
        this.issue = issue;
        this.location = location;

        type = DocumentType.journal;
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
                if(resultSet.next()){
                    lastId = resultSet.getInt(1);
                }
            }

            preparedStatement = Database.connection.prepareStatement("insert into documents(id_journals, location, type) values(?, ?, ?)");
            preparedStatement.setInt(1, lastId);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, "journals");
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error create journal: " + e.toString());
        }
    }
}
