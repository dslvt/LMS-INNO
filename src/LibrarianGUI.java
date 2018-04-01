import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LibrarianGUI extends JFrame{
    private JButton Books = new JButton("Books");
    private JButton Users = new JButton("Users");
    private JButton Tasks = new JButton("Tasks");
    private JButton Request = new JButton("Checked out documents");

    public LibrarianGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 200);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new GridLayout(4, 1, 2, 2));
        containerM.add(Books);
        containerM.add(Users);
        containerM.add(Tasks);
        containerM.add(Request);

        Books.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                LibrarianDocumentGUI books = new LibrarianDocumentGUI();
            }
        });

        Users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianUserOptionsGUI users = new LibrarianUserOptionsGUI();
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
        menuWindow.setVisible(true);
    }
}