import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.util.Vector;

public class TakeBook extends JFrame{
    private JList<String> allBooks;
    private Vector<String> vector = new Vector<String>();;
    private JButton takingBook = new JButton("Take Book");

    public TakeBook(){
        JFrame takeBook = new JFrame();
        takeBook.setBounds(100, 100, 250, 150);
        takeBook.setLocationRelativeTo(null);
        takeBook.setResizable(false);
        takeBook.setTitle("Take Book");
        takeBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerTB = takeBook.getContentPane();
        containerTB.setLayout(new GridLayout(6, 2, 2, 2));
        // for(int i=0; i<numberOfBooks; i++)
        // vector.add(takeBookWithID(i));
        allBooks = new JList<String>(vector);
        containerTB.add(new JScrollPane(allBooks));
        containerTB.add(takingBook);
        takeBook.setVisible(true);
    }

}
