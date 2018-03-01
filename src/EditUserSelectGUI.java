import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserSelectGUI extends JFrame {
    private JButton edit = new JButton("Edit");

    public EditUserSelectGUI() {
        try {
            JFrame tasks = new JFrame();
            tasks.setBounds(100, 100, 500, 400);
            tasks.setLocationRelativeTo(null);
            tasks.setResizable(false);
            tasks.setTitle("Edit user");
            tasks.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = tasks.getContentPane();
            containerTB.setLayout(new BorderLayout());
            Object[][] books = { {"Elvira", "Student"}};
            String[] columnNames = {"Name", "Position"};

            JTable table = new JTable(books, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){
                        new EditUserGUI();
                    }
                    else{
                        String message = "Select a student!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });

            edit.setPreferredSize(new Dimension(250, 40));
            containerTB.add(edit, BorderLayout.SOUTH);
            tasks.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in Tasks " + e.toString());
        }
    }

}