import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.util.ArrayList;

public class AVmaterial extends Document {

    public AVmaterial(){}

    public AVmaterial(String name, ArrayList<String> authors, int cost, ArrayList<String> keywords, boolean isReference){
        this.name = name;
        this.authors = authors;
        this.price = cost;
        this.keywords = keywords;
        this.isReference = isReference;

        type = DocumentType.avmaterial;
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
                preparedStatement = Database.connection.prepareStatement("update av_materials set number = number + 1 where id = " + lastId.toString());
                preparedStatement.executeUpdate();
            }else {
                preparedStatement = Database.connection.prepareStatement("insert into av_materials(title, author, cost, keywords, number, reference) values(?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, this.name);
                preparedStatement.setString(2, this.authors.toString());
                preparedStatement.setInt(3, price);
                preparedStatement.setString(4, this.keywords.toString());
                preparedStatement.setInt(5, 1);
                preparedStatement.setBoolean(6, false);
                preparedStatement.executeUpdate();

                statement = Database.connection.createStatement();
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();");
                if(resultSet.next()){
                    lastId = resultSet.getInt(1);
                }
            }

            preparedStatement = Database.connection.prepareStatement("insert into documents(id_av_materials, location, type, isActive) values(?, ?, ?, ?)");
            preparedStatement.setInt(1, lastId);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, "av_materials");
            preparedStatement.setBoolean(4, isActive);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error create av_material: " + e.toString());
        }
    }
}
