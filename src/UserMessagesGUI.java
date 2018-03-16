import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class UserMessagesGUI extends JFrame{
    private JButton accept = new JButton("OK!");

    public UserMessagesGUI() {
        try {
            JFrame tasks = new JFrame();
            tasks.setBounds(100, 100, 300, 400);
            tasks.setLocationRelativeTo(null);
            tasks.setResizable(false);
            tasks.setTitle("Messages");
            tasks.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = tasks.getContentPane();
            containerTB.setLayout(new BorderLayout());

            String[] columnNames = {"Messages"};

            Patron patron = (Patron) CurrentSession.user;
            ArrayList<Document> documents = patron.getAllRequests();

            Object[][] tasksA = new Object[documents.size()][];
            for (int i = 0; i < documents.size(); i++) {
                tasksA[i] = new Object[1];
                tasksA[i][0] = documents.get(i).name;
            }

            JTable table = new JTable(tasksA, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,150));
            containerTB.add(listScroller, BorderLayout.CENTER);

            accept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){

                    }
                    else{
                        String message = "Select a document!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            accept.setPreferredSize(new Dimension(100, 40));
            containerTB.add(accept, BorderLayout.SOUTH);
            tasks.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in Tasks " + e.toString());
        }
    }

}