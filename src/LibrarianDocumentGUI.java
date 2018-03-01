import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LibrarianDocumentGUI extends JFrame{
    private JButton EditBook = new JButton("EditBook");
    private JButton DeleteBook = new JButton("Delete Book");
    private JButton AddBook = new JButton("Add Book");

    public LibrarianDocumentGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 150);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new GridLayout(3, 1, 2, 2));
        containerM.add(EditBook);
        containerM.add(DeleteBook);
        containerM.add(AddBook);

        EditBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                EditDocumentGUI books = new EditDocumentGUI();
            }
        });

        DeleteBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteDocumentGUI books = new DeleteDocumentGUI();
            }
        });

        AddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDocumentGUI books = new AddDocumentGUI();
            }
        });
        menuWindow.setVisible(true);
    }
}