import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class TakeBook extends JFrame{
    private JList<String> allBooks;
    private Vector<Document> vector;
    private JButton takingBook = new JButton("Take Book");

    private Database db = new Database();

    /**
     * creating take book menu GUI
     */
    public TakeBook() {

        try {

            JFrame takeBook = new JFrame();
            takeBook.setBounds(100, 100, 250, 200);
            takeBook.setLocationRelativeTo(null);
            takeBook.setResizable(false);
            takeBook.setTitle("Take Book");
            takeBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = takeBook.getContentPane();
            containerTB.setLayout(new BorderLayout());

            vector = new Vector(db.getAllDocuments());
            Vector <String> documentNames = new Vector<>();
            for (int i = 0; i < vector.size(); i++){
                documentNames.add(vector.get(i).name);
            }

            allBooks = new JList<String>(documentNames);
            allBooks.setPreferredSize(new Dimension(150, 200));
            allBooks.setLayoutOrientation(JList.VERTICAL);
            allBooks.setVisibleRowCount(0);
            allBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane listScroller = new JScrollPane(allBooks);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            Booking booking = new Booking();

            ArrayList<Integer> documentIds = db.getAllDocumentsIDs();

            takingBook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = allBooks.getSelectedIndex();
                    if(index != -1){
                        int amountOfDays = -1;

                        String time = "";
                        amountOfDays = booking.checkOut(new AVmaterial(documentIds.get(index)) , CurrentSession.user);
                        if(amountOfDays == -1){
                            time = "Sorry, but you cant take this book";
                        }else{
                            time = "You have been taken book on " + Integer.toString(amountOfDays) + " days.";
                        }

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
