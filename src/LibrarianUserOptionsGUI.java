import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class LibrarianUserOptionsGUI extends JFrame{
    private JButton EditUser = new JButton("Edit User");
    private JButton DeleteUser = new JButton("Delete User");
    private JButton AddUser = new JButton("Add User");
    private JButton ShowInfo = new JButton("All Users");
    private JButton Debtors = new JButton("Debtors");

    public LibrarianUserOptionsGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 385);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());
        String[] columnNames = {"Name", "Login"};
        ArrayList<Patron> patrons = Database.getAllPatrons();
        Object[][] names = new Object[patrons.size()][2];
        for(int i = 0; i < names.length; i++){
            names[i][0] = patrons.get(i).name;
            names[i][1] = patrons.get(i).phoneNumber;
        }
        JTable table = new JTable(names, columnNames);
        JScrollPane listScroller = new JScrollPane(table);
        listScroller.setPreferredSize(new Dimension(240, 118));
        containerM.add(listScroller);
        EditUser.setPreferredSize(new Dimension(240, 40));
        containerM.add(EditUser);
        DeleteUser.setPreferredSize(new Dimension(240, 40));
        containerM.add(DeleteUser);
        AddUser.setPreferredSize(new Dimension(240, 40));
        containerM.add(AddUser);
        ShowInfo.setPreferredSize(new Dimension(240, 40));
        containerM.add(ShowInfo);
        Debtors.setPreferredSize(new Dimension(240, 40));
        containerM.add(Debtors);
        Debtors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DebtorsGUI debtors = new DebtorsGUI();
            }
        });

        // I have changed AllUsers directly to ShowInfo
        ShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if(index != -1){
                    ArrayList<Document> docs = Database.getUserDocuments(patrons.get(index));
                    String message = "";
                    for (int i = 0; i < docs.size(); i++) {
                        message += docs.get(i) + "\n";
                    }
                    if (message.equals("")){
                        message = "User has no books";
                    }
                    JOptionPane.showMessageDialog(null,message, "Info", JOptionPane.PLAIN_MESSAGE);
                } else{
                    String message = "Select a book!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
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
 