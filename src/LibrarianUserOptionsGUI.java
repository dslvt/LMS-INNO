import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class LibrarianUserOptionsGUI extends JFrame {
    private JButton EditUser = new JButton("Edit User");
    private JButton DeleteUser = new JButton("Delete User");
    private JButton AddUser = new JButton("Add User");
    private JButton ShowInfo = new JButton("Show Info");
    private JButton Debtors = new JButton("Debtors");
    private TableRowSorter<TableModel> rowSorter;
    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filter");
    private JLabel jLabel1 = new JLabel("Select search criteria");
    private JLabel jLabel2 = new JLabel("Search");
    private JComboBox selectForSearch;
    private JButton upgrade = new JButton("Upgrade");
    private JComboBox selectForUpgrade;

    public LibrarianUserOptionsGUI(int user_id) {
        JFrame menuWindow = new JFrame();
        if (Database.isLibrarianPriv1(user_id))
            menuWindow.setBounds(100, 100, 300, 347);
        if (Database.isLibrarianPriv2(user_id))
            menuWindow.setBounds(100, 100, 300, 387);
        if(Database.isLibrarianPriv3(user_id))
            menuWindow.setBounds(100, 100, 300, 427);
        if (Database.isAdmin(user_id))
            menuWindow.setBounds(100, 100, 300, 467);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());
        String[] selecting = {"All","Name", "Login", "Debt", "Type", "Address"};
        selectForSearch = new JComboBox(selecting);
        selectForSearch.setSelectedIndex(0);
        selectForSearch.setPreferredSize(new Dimension(130, 20));
        jLabel1.setPreferredSize(new Dimension(130, 20));
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(jLabel1);
        panel1.add(selectForSearch);
        panel1.setPreferredSize(new Dimension(290, 25));
        containerM.add(panel1);
        String[] columnNames = {"Name", "Login", "Debt", "Type", "Address", "Id"};
        ArrayList<ArrayList<String>> users = new ArrayList<>();
        try {
            Statement statement = Database.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users ORDER BY id");
            int count = 0;
            while (rs.next()) {
                boolean isActive = rs.getBoolean("isActive");
                String type = rs.getString("type");
                if((!type.equals("admin")&&Database.isAdmin(CurrentSession.user.id)||(isActive&&!Database.isAdmin(CurrentSession.user.id)))){
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
                    int hah = rs.getInt("id");
                    users.get(count).add(Integer.toString(hah));

                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in deleteuserGUI " + e.toString());
        }
        Object[][] usersAr = new Object[users.size()][6];
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < 6; j++) {
                usersAr[i][j] = users.get(i).get(j);
            }
        }
        DefaultTableModel model = new DefaultTableModel(usersAr, columnNames);
        JTable table = new JTable(model);
        JScrollPane listScroller = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        jtfFilter.setPreferredSize(new Dimension(220, 20));
        jLabel2.setPreferredSize(new Dimension(50, 20));
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.add(jLabel2);
        panel2.add(jtfFilter);
        panel2.setPreferredSize(new Dimension(290, 25));
        containerM.add(panel2);
        listScroller.setPreferredSize(new Dimension(290, 100));
        containerM.add(listScroller);
        EditUser.setPreferredSize(new Dimension(290, 40));
        containerM.add(EditUser);
        EditUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if (index != -1) {
                    menuWindow.dispose();
                    CurrentSession.editUser = Database.getPatronByNumber(users.get(index).get(1));
                    EditUserGUI edit = new EditUserGUI(user_id);
                    CurrentSession.editUser.ModifyUserDB(CurrentSession.editUser.name, CurrentSession.editUser.password, CurrentSession.editUser.phoneNumber,
                            CurrentSession.editUser.address, CurrentSession.editUser.isFacultyMember, CurrentSession.editUser.debt, Patron.getParsedPatronType(CurrentSession.editUser.type), false, CurrentSession.user.id);
                } else {
                    String message = "Select a student!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        if(Database.isLibrarianPriv2(user_id) | Database.isLibrarianPriv3(user_id) | Database.isAdmin(user_id)) {
            AddUser.setPreferredSize(new Dimension(290, 40));
            containerM.add(AddUser);
            AddUser.addActionListener(new RegisterWindow());
            AddUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Database.isLibrarianPriv2(CurrentSession.user.id)) {
                        menuWindow.dispose();
                    }
                    else{
                        System.out.println("Error in AddUserGUI: user doesn't have access to add new user");
                    }
                }
            });
        }
        if(Database.isLibrarianPriv3(user_id) | Database.isAdmin(user_id)){
            DeleteUser.setPreferredSize(new Dimension(290, 40));
            containerM.add(DeleteUser);
            DeleteUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if (index != -1) {
                        Patron patron = Database.getPatronByNumber(users.get(index).get(1));
                        patron.DeleteUserDB(CurrentSession.user.id);
                    } else {
                        String message = "Select a student!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
        }
        if(Database.isAdmin(user_id)) {
            upgrade.setPreferredSize(new Dimension(290, 40));
            String[] searching = {"priv1", "priv2", "priv3"};
            selectForUpgrade = new JComboBox(searching);
            selectForUpgrade.setSelectedIndex(0);
            JPanel panelka = new JPanel(new GridLayout(1,2,2,2));
            panelka.add(selectForSearch);
            panelka.add(upgrade);
            containerM.add(panelka);
            upgrade.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if (index != -1){
                        //menuWindow.dispose();
                        int userId = Integer.parseInt(users.get(index).get(5));
                        if(Database.isLibrarian(userId)) {
                            Database.upgradeToLibrarian(Database.getLibrarianById(userId),(String)selectForUpgrade.getSelectedItem(),CurrentSession.user.id);
                            System.out.println("librarian");
                        }
                        else{
                            Database.upgradeToLibrarian(Database.getPatronById(userId),(String)selectForUpgrade.getSelectedItem(),CurrentSession.user.id);
                            System.out.println("patron");
                        }
                    }
                    else {
                        String message = "Select user!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
        }
        ShowInfo.setPreferredSize(new Dimension(290, 40));
        containerM.add(ShowInfo);
        // I have changed AllUsers directly to ShowInfo
        ShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if (index != -1) {
                    Patron patron = Database.getPatronByNumber(users.get(index).get(1));
                    ArrayList<Document> docs = Database.getUserDocuments(patron);
                    String message = "";
                    for (int i = 0; i < docs.size(); i++) {
                        message += docs.get(i).name + "\n";
                    }
                    if (message.equals("")) {
                        message = "User has no books";
                    }
                    JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.PLAIN_MESSAGE);
                } else {
                    String message = "Select user!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        Debtors.setPreferredSize(new Dimension(290, 40));
        containerM.add(Debtors);
        Debtors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DebtorsGUI debtors = new DebtorsGUI();
            }
        });
        jtfFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    int no = selectForSearch.getSelectedIndex();
                    if(no == 0)
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    else
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, no-1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    int no = selectForSearch.getSelectedIndex();
                    if(no == 0)
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    else
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, no-1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        menuWindow.setVisible(true);
    }
}
 