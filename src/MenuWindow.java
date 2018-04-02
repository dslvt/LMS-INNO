import java.awt.*;
import java.awt.event.*;
import java.awt.image.DataBufferShort;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

public class MenuWindow extends JFrame {
    private JButton takeBook = new JButton("Take Book");
    private JButton myBooks = new JButton("My Books");
    private JButton messagesButton = new JButton("Messages");
    private JButton myInfo = new JButton("My Info");
    private JButton logOut = new JButton("Log Out");

    /**
     * creating menu window GUI
     */
    public MenuWindow(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 250);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Sign Up");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new GridLayout(5, 1, 2, 2));
        containerM.add(takeBook);
        containerM.add(myBooks);
        containerM.add(messagesButton);
        containerM.add(myInfo);
        containerM.add(logOut);
        takeBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                TakeBook books = new TakeBook();
            }
        });
        myBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                MyBooksGUI myBooksGUI = new MyBooksGUI();
            }
        });
        messagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserMessagesGUI userMessagerGUI = new UserMessagesGUI();
            }
        });
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentSession.user = null;
                CurrentSession.editUser = null;
                CurrentSession.editDocument = null;
                menuWindow.dispose();
                FirstWindow restartFirstWindow = new FirstWindow();
                restartFirstWindow.setVisible(true);
            }
        });
        myInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Booking booking = new Booking();
                    int debt = 0;
                    Patron patron = (Patron)CurrentSession.user;
                    ArrayList<Document> documents = Database.getUserDocuments(patron);
                    for(int i=0; i<documents.size(); i++){
                        debt += booking.countOverdueCost(documents.get(i));
                    }
                    String message = "Name: " + CurrentSession.user.name + "\n";
                    message += "Phone Number: " + CurrentSession.user.phoneNumber + "\n";
                    message += "Address: " + CurrentSession.user.address + "\n";
                    message += "Debt: " + debt + "\n";
                    JOptionPane.showMessageDialog(null, message, "My Info", JOptionPane.PLAIN_MESSAGE);
                } catch(Exception q){
                    System.out.println("Error in menuWindow" + e.toString());
                }

            }
        });
        menuWindow.setVisible(true);
    }
}
