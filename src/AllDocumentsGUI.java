import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.*;

public class AllDocumentsGUI extends JFrame{

    private Database db = new Database();

    public AllDocumentsGUI() {

        try {
            JFrame users = new JFrame();
            users.setBounds(100, 100, 250, 200);
            users.setLocationRelativeTo(null);
            users.setResizable(false);
            users.setTitle("All documents");
            users.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = users.getContentPane();
            containerTB.setLayout(new BorderLayout());

            String[] columnNames = {"Name", "Author", "Price"};
            ArrayList<Document> docs = db.getAllDocuments();
            Object[][] names = new Object[docs.size()][3];
            for(int i = 0; i < names.length; i++){
                names[i][0] = docs.get(i).name;
                names[i][1] = docs.get(i).authors;
                names[i][2] = docs.get(i).price;
            }

            JTable table = new JTable(names, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            users.setVisible(true);
        }catch (Exception e){
            System.out.println("Error in AllDocumentsGUI " + e.toString());
        }
    }

}
