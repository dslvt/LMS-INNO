import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class TakeBook extends JFrame{
    private JList<String> allBooks;
    private Vector<String> vector = new Vector<String>();;
    private JButton takingBook = new JButton("Take Book");

    private ArrayList<Book> books;
    private Database db = new Database();

    public TakeBook() {

        try {

            JFrame takeBook = new JFrame();
            takeBook.setBounds(100, 100, 250, 150);
            takeBook.setLocationRelativeTo(null);
            takeBook.setResizable(false);
            takeBook.setTitle("Take Book");
            takeBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = takeBook.getContentPane();
            containerTB.setLayout(new GridLayout(6, 2, 2, 2));

            books = db.getAllBooks();
            for (int i = 0; i < books.size(); i++) {
                vector.add(books.get(i).name);
            }

            // for(int i=0; i<numberOfBooks; i++)
            // vector.add(takeBookWithID(i));
            allBooks = new JList<String>(vector);
            allBooks.setPreferredSize(new Dimension(150, 200));
            allBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            containerTB.add(new JScrollPane(allBooks));
            takingBook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = allBooks.getSelectedIndex();
                    if(index != -1){
                        
                        takeBook.setVisible(false);
                    } else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            containerTB.add(takingBook);
            takeBook.setVisible(true);
        }catch (Exception e){
            System.out.println("Error in takebook " + e.toString());
        }
    }

}
