import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

public class MenuWindow extends JFrame {
    private JButton takeBook = new JButton("Take Book");
    private JButton myBooks = new JButton("My Books");
    private JButton messagesButton = new JButton("Messages");
    private JButton logOut = new JButton("Log out");

    /**
     * creating menu window GUI
     */
    public MenuWindow(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 200);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Sign Up");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new GridLayout(4, 1, 2, 2));
        containerM.add(takeBook);
        containerM.add(myBooks);
        containerM.add(messagesButton);
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
        menuWindow.setVisible(true);
    }
}
