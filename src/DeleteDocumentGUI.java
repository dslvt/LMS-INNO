import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteDocumentGUI  extends JFrame {
    private JButton deletingBook = new JButton("Delete Book");


    public DeleteDocumentGUI() {
        try {
            JFrame deleteBook = new JFrame();
            deleteBook.setBounds(100, 100, 250, 200);
            deleteBook.setLocationRelativeTo(null);
            deleteBook.setResizable(false);
            deleteBook.setTitle("Delete Book");
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

            deletingBook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if(index != -1){
                        deleteBook.setVisible(false);
                        String message = "Book succesfully deleted!";
                        JOptionPane.showMessageDialog(null, message, "New Window", JOptionPane.PLAIN_MESSAGE);
                    }
                    else{
                        String message = "Select a book!\n";
                        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            deletingBook.setPreferredSize(new Dimension(250, 40));
            containerTB.add(deletingBook, BorderLayout.SOUTH);
            deleteBook.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in DeleteBook " + e.toString());
        }
    }
}