import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LibrarianUserOptionsGUI extends JFrame{
    private JButton EditUser = new JButton("Edit User");
    private JButton DeleteUser = new JButton("Delete User");
    private JButton AddUser = new JButton("Add User");
    private JButton AllUsers = new JButton("All Users");

    public LibrarianUserOptionsGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 200);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new GridLayout(4, 1, 2, 2));
        containerM.add(EditUser);
        containerM.add(DeleteUser);
        containerM.add(AddUser);
        containerM.add(AllUsers);

        AllUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllUsersGUI users = new AllUsersGUI();
            }
        });

        EditUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditUserSelectGUI edit = new EditUserSelectGUI();
            }
        });

        DeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteUserGUI delete = new DeleteUserGUI();
            }
        });

        AddUser.addActionListener(new RegisterWindow());
        menuWindow.setVisible(true);
    }
}
 