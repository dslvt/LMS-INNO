import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class LibrarianUserOptionsGUI extends JFrame{
    private JButton EditUser = new JButton("Edit User");
    private JButton DeleteUser = new JButton("Delete User");
    private JButton AddUser = new JButton("Add User");
    private JButton ShowInfo = new JButton("Show Info");
    private JButton Debtors = new JButton("Debtors");

    public LibrarianUserOptionsGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 300, 368);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());

        String[] columnNames = {"Name", "Login", "Debt", "Type", "Address"};
        ArrayList<ArrayList<String>> users = new ArrayList<>();
        try{
            Statement statement = Database.connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users order by id");

            int count = 0;
            while (rs.next()){
                boolean isActive = rs.getBoolean("isActive");
                if(isActive) {
                    users.add(new ArrayList<String>());
                    users.get(count).add(rs.getString("name"));
                    users.get(count).add(rs.getString("phoneNumber"));
                    users.get(count).add(rs.getString("debt"));

                    String userType = "";
                    if (rs.getBoolean("isLibrarian")) {
                        userType = "Librarian";
                    } else if (rs.getBoolean("isFacultyMember")) {
                        userType = "Faculty Member";
                    } else {
                        userType = "Patron";
                    }

                    users.get(count).add(userType);
                    users.get(count).add(rs.getString("address"));

                    count++;
                }
            }
        }catch (Exception e){
            System.out.println("Error in deleteuserGUI " + e.toString());
        }
        Object[][] usersAr = new Object[users.size()][5];
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < 5; j++) {
                usersAr[i][j] = users.get(i).get(j);
            }
        }

        JTable table = new JTable(usersAr, columnNames);
        JScrollPane listScroller = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listScroller.setPreferredSize(new Dimension(290,100));
        containerM.add(listScroller);
        EditUser.setPreferredSize(new Dimension(290, 40));
        containerM.add(EditUser);
        DeleteUser.setPreferredSize(new Dimension(290, 40));
        containerM.add(DeleteUser);
        AddUser.setPreferredSize(new Dimension(290, 40));
        containerM.add(AddUser);
        ShowInfo.setPreferredSize(new Dimension(290, 40));
        containerM.add(ShowInfo);
        Debtors.setPreferredSize(new Dimension(290, 40));
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
                    Patron patron = Database.getPatronByNumber(users.get(index).get(1));
                    ArrayList<Document> docs = Database.getUserDocuments(patron);
                    String message = "";
                    for (int i = 0; i < docs.size(); i++) {
                        message += docs.get(i).name + "\n";
                    }
                    if (message.equals("")){
                        message = "User has no books";
                    }
                    JOptionPane.showMessageDialog(null,message, "Info", JOptionPane.PLAIN_MESSAGE);
                } else{
                    String message = "Select user!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        EditUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if(index != -1){
                    CurrentSession.editUser = Database.getPatronByNumber(users.get(index).get(1));
                    EditUserGUI edit =  new EditUserGUI();
                    CurrentSession.editUser.ModifyUserDB(CurrentSession.editUser.name, CurrentSession.editUser.password, CurrentSession.editUser.phoneNumber,
                            CurrentSession.editUser.address, CurrentSession.editUser.isFacultyMember, CurrentSession.editUser.debt, false, CurrentSession.user.id);
                }
                else{
                    String message = "Select a student!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        DeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if(index != -1){
                    Patron patron = Database.getPatronByNumber(users.get(index).get(1));
                    patron.DeleteUserDB(CurrentSession.user.id);
                }
                else{
                    String message = "Select a student!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        AddUser.addActionListener(new RegisterWindow());
        menuWindow.setVisible(true);
    }
}
 