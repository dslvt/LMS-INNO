import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class Article extends Document {
    public Journal journal;

    public Article(String name, ArrayList<String> authors, Journal journal){
        this.name = name;
        this.authors = authors;
        this.journal = journal;

        type = DocumentType.article;
    }

    @Override
    public void CreateDocumentInDB() {
        Statement statement;
        ResultSet resultSet;
        PreparedStatement preparedStatement;

        try{
            int journalID = Database.isDocumentExistByType("journals", journal);
            int lastId = -1;

            preparedStatement = Database.connection.prepareStatement("insert into journal_articles(author, title, journal_id) values(?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, authors.toString());
            preparedStatement.setInt(3, journalID);

            preparedStatement.executeUpdate();

            statement = Database.connection.createStatement();
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
            if(resultSet.next()){
                lastId = resultSet.getInt(1);
            }

            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error create journal article: " + e.toString());
        }
    }
}
