import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LibrarianDocumentGUI extends JFrame{
    private JButton EditBook = new JButton("EditBook");
    private JButton DeleteBook = new JButton("Delete Book");
    private JButton AddBook = new JButton("Add Book");
    private JButton Create = new JButton("Create Copy");
    private JButton AllDocuments = new JButton("All Documents");

    public LibrarianDocumentGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 250);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new GridLayout(5, 1, 2, 2));
        containerM.add(EditBook);
        containerM.add(DeleteBook);
        containerM.add(AddBook);
        containerM.add(Create);
        containerM.add(AllDocuments);

        AllDocuments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllDocumentsGUI docs = new AllDocumentsGUI();
            }
        });

        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateCopyGUI create = new CreateCopyGUI();
            }
        });

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