import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class LibrarianDocumentGUI extends JFrame{
    private JButton EditBook = new JButton("EditBook");
    private JButton DeleteBook = new JButton("Delete Book");
    private JButton AddBook = new JButton("Add Book");
    private JButton Create = new JButton("Create Copy");
    private JButton AllDocuments = new JButton("All Documents");

    public LibrarianDocumentGUI(){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 385);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());

        ArrayList<Document> documents = Database.getAllDocuments();

        Object[][] books = new Object[documents.size()][];

        for (int i = 0; i < documents.size(); i++) {
            books[i] = new Object[4];
            books[i][0] = documents.get(i).name;
            books[i][1] = documents.get(i).authors;
            books[i][2] = documents.get(i).location;
            books[i][3] = documents.get(i).price;
        }

        String[] columnNames = {"Name", "Authors", "Location", "Price"};

        JTable table = new JTable(books, columnNames);
        JScrollPane listScroller = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        listScroller.setPreferredSize(new Dimension(240,118));
        containerM.add(listScroller);
        EditBook.setPreferredSize(new Dimension(240, 40));
        containerM.add(EditBook);
        DeleteBook.setPreferredSize(new Dimension(240, 40));
        containerM.add(DeleteBook);
        AddBook.setPreferredSize(new Dimension(240, 40));
        containerM.add(AddBook);
        Create.setPreferredSize(new Dimension(240, 40));
        containerM.add(Create);
        AllDocuments.setPreferredSize(new Dimension(240, 40));
        containerM.add(AllDocuments);

//111
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
                int index = table.getSelectedRow();
                if(index > 0) {
                    if (documents.get(index).type == DocumentType.book) {
                        AddBookGUI book = new AddBookGUI();
                    } else if (documents.get(index).type == DocumentType.journal) {
                        AddJournalGUI journal = new AddJournalGUI();
                    } else if (documents.get(index).type == DocumentType.avmaterial) {
                        AddAVmaterialGUI AVmaterial = new AddAVmaterialGUI();
                    }
                }
                else{
                    String message = "No row is selected\n" + "You need to select one to edit";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
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