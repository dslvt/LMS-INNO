import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDocumentGUI  extends JFrame {
    private JButton editingBook = new JButton("Edit Book");


    public EditDocumentGUI() {
        try {
            JFrame deleteBook = new JFrame();
            deleteBook.setBounds(100, 100, 250, 200);
            deleteBook.setLocationRelativeTo(null);
            deleteBook.setResizable(false);
            deleteBook.setTitle("Edit Book");
            deleteBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = deleteBook.getContentPane();
            containerTB.setLayout(new BorderLayout());

            Object[][] books = {{"Lol,kek,cheburek", new Integer(5)}};
            String[] columnNames = {"Name", "Amount"};

            JTable table = new JTable(books, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            editingBook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    int type = 1;
                    if(type == 0){
                        AddBookGUI book = new AddBookGUI();
                    }
                    else if (type == 1){
                        AddJournalGUI journal = new AddJournalGUI();
                    }
                    else if (type == 2){
                        AddAVmaterialGUI AVmaterial = new AddAVmaterialGUI();
                    }
                    else if (type == 3){

                    }
                }
            });
            editingBook.setPreferredSize(new Dimension(250, 40));
            containerTB.add(editingBook, BorderLayout.SOUTH);
            deleteBook.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in DeleteBook " + e.toString());
        }
    }

}