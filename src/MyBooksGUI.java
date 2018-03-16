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

public class MyBooksGUI extends JFrame{
    private JList<String> allBooks;
    private Vector<Document> vector;
    private JButton takingBook = new JButton("Return book");

    /**
     * creating take book menu GUI
     */
    public MyBooksGUI() {

        try {

            JFrame takeBook = new JFrame();
            takeBook.setBounds(100, 100, 250, 200);
            takeBook.setLocationRelativeTo(null);
            takeBook.setResizable(false);
            takeBook.setTitle("My books");
            takeBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = takeBook.getContentPane();
            containerTB.setLayout(new BorderLayout());

            //spisk books
            vector = new Vector(Database.getUserDocuments((Patron) CurrentSession.user));
            Vector <String> documentNames = new Vector<>();
            for (int i = 0; i < vector.size(); i++){
                documentNames.add(vector.get(i).name);
            }

            String[] columnNames = {"Name", "Author", "Return date"};

            Object[][] docs = new Object[vector.size()][3];

            for (int i = 0; i < docs.length; i++) {
                docs[i][0] = vector.get(i).name;
                docs[i][1] = vector.get(i).authors;
                docs[i][2] = Database.getDocumentReturnDate(vector.get(i));
            }

            JTable table = new JTable(docs, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            takingBook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = allBooks.getSelectedIndex();
                    if(index != -1){
                        EventManager eventManager = new EventManager();
                        eventManager.CreateQuery(new LibTask(vector.get(index), (Patron)CurrentSession.user, "return"));

                        String time = "";
                        JOptionPane.showMessageDialog(null, time, "", JOptionPane.PLAIN_MESSAGE);

                        takeBook.setVisible(false);
                    } else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            takingBook.setPreferredSize(new Dimension(250, 40));
            containerTB.add(takingBook, BorderLayout.SOUTH);
            takeBook.setVisible(true);
        }catch (Exception e){
            System.out.println("Error in takebook " + e.toString());
        }
    }

}
