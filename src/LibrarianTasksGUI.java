import java.awt.*;
import java.awt.event.*;
import javax.print.DocFlavor;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class LibrarianTasksGUI extends JFrame{
    private JButton accept = new JButton("Accept");
    private JButton decline = new JButton("Decline");

    public LibrarianTasksGUI() {
        try {
            JFrame tasks = new JFrame();
            tasks.setBounds(100, 100, 500, 400);
            tasks.setLocationRelativeTo(null);
            tasks.setResizable(false);
            tasks.setTitle("Tasks");
            tasks.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = tasks.getContentPane();
            containerTB.setLayout(new BorderLayout());
            containerTB.setLayout(new GridLayout(3, 1, 1, 1));
            Object[][] books = {{"Nigu & listochki", "Elvira", "Student"}};
            String[] columnNames = {"Book", "Name", "Position"};

            JTable table = new JTable(books, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            accept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){

                    }
                    else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            decline.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){

                    }
                    else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            accept.setPreferredSize(new Dimension(250, 10));
            containerTB.add(accept, BorderLayout.SOUTH);
            containerTB.add(accept);
            decline.setPreferredSize(new Dimension(250, 10));
            containerTB.add(decline, BorderLayout.SOUTH);
            tasks.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in Tasks " + e.toString());
        }
    }

}