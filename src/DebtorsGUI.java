import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.*;

public class DebtorsGUI extends JFrame{

    private JButton ShowInfo = new JButton("Show info");

    private Librarian lb = new Librarian();
    private Database db = new Database();
    public DebtorsGUI() {

        try {
            JFrame users = new JFrame();
            users.setBounds(100, 100, 250, 200);
            users.setLocationRelativeTo(null);
            users.setResizable(false);
            users.setTitle("Debtors");
            users.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = users.getContentPane();
            containerTB.setLayout(new BorderLayout());

            String[] columnNames = {"Name", "Login"};
            ArrayList<Patron> patrons = lb.getDebtors();
            Object[][] names = new Object[patrons.size()][2];
            for(int i = 0; i < names.length; i++){
                names[i][0] = patrons.get(i).name;
                names[i][1] = patrons.get(i).phoneNumber;
            }

            JTable table = new JTable(names, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            Booking booking = new Booking();

            ShowInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){
                        ArrayList<Document> docs = db.getUserDocuments(patrons.get(index));
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
            ShowInfo.setPreferredSize(new Dimension(250, 40));
            containerTB.add(ShowInfo, BorderLayout.SOUTH);
            users.setVisible(true);
        }catch (Exception e){
            System.out.println("Error in AllUsersGUI " + e.toString());
        }
    }

}
