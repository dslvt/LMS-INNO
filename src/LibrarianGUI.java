import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LibrarianGUI extends JFrame{
    private JButton Books = new JButton("Books");
    private JButton Users = new JButton("Users");
    private JButton Tasks = new JButton("Tasks");
    private JButton Request = new JButton("Checked out documents");
    private JButton LastActions = new JButton("Last actions");
    private JButton logOut = new JButton("Log out");

    public LibrarianGUI(int user_id){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 250);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        if(Database.isAdmin(user_id))
            containerM.setLayout(new GridLayout(6, 1, 2, 2));
        else
            containerM.setLayout(new GridLayout(5, 1, 2, 2));
        containerM.add(Books);
        containerM.add(Users);
        containerM.add(Tasks);
        containerM.add(Request);
        if(Database.isAdmin(user_id))
            containerM.add(LastActions);
        containerM.add(logOut);

        Books.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                LibrarianDocumentGUI books = new LibrarianDocumentGUI(user_id);
            }
        });

        Users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianUserOptionsGUI users = new LibrarianUserOptionsGUI(user_id);
            }
        });

        Tasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianTasksGUI tasks = new LibrarianTasksGUI();
            }
        });

        Request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianRequestGUI request = new LibrarianRequestGUI();
            }
        });

        LastActions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LastActionsGUI lastActionsGUI = new LastActionsGUI();
            }
        });

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentSession.user = null;
                CurrentSession.editUser = null;
                CurrentSession.editDocument = null;
                CurrentSession.setDate = 0L;
                menuWindow.dispose();
                FirstWindow restartFirstWindow = new FirstWindow();
                restartFirstWindow.setVisible(true);
            }
        });
        menuWindow.setVisible(true);
    }
}