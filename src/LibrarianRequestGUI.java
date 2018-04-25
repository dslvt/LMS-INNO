import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class LibrarianRequestGUI extends JFrame{
    private JButton SendRequest = new JButton("Send request");

    /**
     * librarian request GUI
     * used to send requests
     */
    public LibrarianRequestGUI() {
        try {
            //init window and set plane properties
            JFrame takeBook = new JFrame();
            takeBook.setBounds(100, 100, 250, 200);
            takeBook.setLocationRelativeTo(null);
            takeBook.setResizable(false);
            takeBook.setTitle("Send request");
            takeBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = takeBook.getContentPane();
            containerTB.setLayout(new BorderLayout());

            //parsed all documents with users and insert in into table
            String[] columnNames = {"User", "Book"};
            ArrayList<Pair<Document, Patron>> requests = Database.getAllDocumentsWithUsers();
            Object[][] requestA = new Object[requests.size()][2];
            for(int i = 0; i < requestA.length; i++){
                requestA[i][0] = requests.get(i).second.name;
                requestA[i][1] = requests.get(i).first.name;
            }

            //init table and set table properties
            JTable table = new JTable(requestA, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            //send selected request
            SendRequest.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){
                        Librarian lib = new Librarian();
                        lib.sendRequest(requests.get(index).first, requests.get(index).second);
                        takeBook.setVisible(false);
                        String message = "You sent request!";
                        JOptionPane.showMessageDialog(null, message, "New Window", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            //close window
            SendRequest.setPreferredSize(new Dimension(250, 40));
            containerTB.add(SendRequest, BorderLayout.SOUTH);
            takeBook.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in Send Request " + e.toString());
        }
    }

}
