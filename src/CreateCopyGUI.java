import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class CreateCopyGUI extends JFrame{
    private JButton Create = new JButton("Create Copy");

    /**
     * init gui
     */
    public CreateCopyGUI() {
        try {
            JFrame takeBook = new JFrame();
            takeBook.setBounds(100, 100, 250, 200);
            takeBook.setLocationRelativeTo(null);
            takeBook.setResizable(false);
            takeBook.setTitle("Create copy");
            takeBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = takeBook.getContentPane();
            containerTB.setLayout(new BorderLayout());

            String[] columnNames = {"Document", "Amount"};

            //find all documents without copies and parse it in table
            ArrayList<Pair<Document, Integer>> documents = Database.getAllDocumentsWithoutCopies();
            Object[][] books = new Object[documents.size()][2];
            for (int i = 0; i < documents.size(); i++) {
                books[i][0] = documents.get(i).first.name;
                books[i][1] = documents.get(i).second;
            }

            //init gui elements
            JTable table = new JTable(books, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            //find selected doc and create copy
            Create.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){
                        Document document = documents.get(index).first;
                        document.location = "loc";
                        document.addCopies(1, CurrentSession.user.id);

                        takeBook.setVisible(false);
                        String message = "You created one copy of document";
                        JOptionPane.showMessageDialog(null, message, "New Window", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            //close window
            Create.setPreferredSize(new Dimension(250, 40));
            containerTB.add(Create, BorderLayout.SOUTH);
            takeBook.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in Send Request " + e.toString());
        }
    }
}
