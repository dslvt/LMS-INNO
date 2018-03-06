import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class AllUsersGUI extends JFrame{

    private JList<String> allUsers;
    private Vector<User> vector;
    private JButton ShowInfo = new JButton("Show info");

    private Database db = new Database();

    public AllUsersGUI() {

        try {
            JFrame users = new JFrame();
            users.setBounds(100, 100, 250, 200);
            users.setLocationRelativeTo(null);
            users.setResizable(false);
            users.setTitle("All users");
            users.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = users.getContentPane();
            containerTB.setLayout(new BorderLayout());

            ArrayList<Integer> documentIds = db.getAllDocumentsIDs();
            /*vector = new Vector(db.getAllDocuments());
            Vector <String> documentNames = new Vector<>();
            for (int i = 0; i < vector.size(); i++){
                if(true){

                }
            }*/
            Vector <String > vusers = new Vector<>();
            vusers.add("her");
            vusers.add("his");
            allUsers = new JList<String>(vusers);
            allUsers.setPreferredSize(new Dimension(150, 200));
            allUsers.setLayoutOrientation(JList.VERTICAL);
            allUsers.setVisibleRowCount(0);
            allUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane listScroller = new JScrollPane(allUsers);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            Booking booking = new Booking();

            ShowInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = allUsers.getSelectedIndex();
                    if(index != -1){
                        String message = "Kek\n Kek \n kek\n kek\n kek \n kek\n kek\n";
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
            System.out.println("Error in takebook " + e.toString());
        }
    }

}
