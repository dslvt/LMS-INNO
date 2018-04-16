import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class LibrarianDocumentGUI extends JFrame{
    private JButton EditBook = new JButton("EditBook");
    private JButton DeleteBook = new JButton("Delete Book");
    private JButton AddBook = new JButton("Add Book");
    private JButton Create = new JButton("Create Copy");
    private JButton Queue = new JButton("Queue");
    private JButton outstandingRequest = new JButton("Outstanding Request");
    private JTable table;
    private JScrollPane listScroller;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filter");
    private JLabel jLabel = new JLabel("Search");

    public LibrarianDocumentGUI() {
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 300, 460);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());

        ArrayList<Document> documents = Database.getAllDocuments();
        Object[][] books = new Object[documents.size()][];

        for (int i = 0; i < documents.size(); i++) {
            books[i] = new Object[5];
            books[i][0] = documents.get(i).name;
            books[i][1] = documents.get(i).authors;
            books[i][2] = documents.get(i).location;
            books[i][3] = documents.get(i).price;
            books[i][4] = documents.get(i).type;
        }

        String[] columnNames = {"Name", "Authors", "Location", "Price", "Type"};

        DefaultTableModel model = new DefaultTableModel(books, columnNames);
        table = new JTable(model);
        listScroller = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        jtfFilter.setPreferredSize(new Dimension(220, 20));
        jLabel.setPreferredSize(new Dimension(50, 20));
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(jLabel);
        panel.add(jtfFilter);
        panel.setPreferredSize(new Dimension(290, 25));
        containerM.add(panel);
        listScroller.setPreferredSize(new Dimension(290, 118));
        containerM.add(listScroller);
        EditBook.setPreferredSize(new Dimension(290, 40));
        containerM.add(EditBook);
        DeleteBook.setPreferredSize(new Dimension(290, 40));
        containerM.add(DeleteBook);
        AddBook.setPreferredSize(new Dimension(290, 40));
        containerM.add(AddBook);
        Create.setPreferredSize(new Dimension(290, 40));
        containerM.add(Create);
        Queue.setPreferredSize(new Dimension(290, 40));
        containerM.add(Queue);
        outstandingRequest.setPreferredSize(new Dimension(290, 40));
        containerM.add(outstandingRequest);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        Queue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuWindow.dispose();
                int index = table.getSelectedRow();
                CurrentSession.editDocument = documents.get(index);
                DocumentQueueGUI queue = new DocumentQueueGUI();
            }
        });

        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if(index != -1){
                    menuWindow.dispose();
                    Document document = documents.get(index);
                    document.location = "its not important";
                    document.addCopies(1, CurrentSession.user.id);
                    String message = "You created one copy of document";
                    JOptionPane.showMessageDialog(null, message, "New Window", JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    String message = "Select a book!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
                LibrarianDocumentGUI restart = new LibrarianDocumentGUI();
            }
        });

        EditBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if (index != -1) {
                    menuWindow.dispose();
                    CurrentSession.editDocument = documents.get(index);
                    if (documents.get(index).type == DocumentType.book) {
                        AddBookGUI book = new AddBookGUI();
                    } else if (documents.get(index).type == DocumentType.journal) {
                        AddJournalGUI journal = new AddJournalGUI();
                    } else if (documents.get(index).type == DocumentType.av_material) {
                        AddAVmaterialGUI AVmaterial = new AddAVmaterialGUI();
                    }
                } else {
                    String message = "No row is selected\n" + "You need to select one to edit";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        DeleteBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if (index != -1) {
                    menuWindow.dispose();
                    documents.get(index).DeleteFromDB(CurrentSession.user.id);
                    String message = "Book succesfully deleted!";
                    JOptionPane.showMessageDialog(null, message, "New Window", JOptionPane.PLAIN_MESSAGE);
                } else {
                    String message = "Select a book!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
                LibrarianDocumentGUI restart = new LibrarianDocumentGUI();
            }
        });

        AddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuWindow.dispose();
                AddDocumentGUI books = new AddDocumentGUI();
            }
        });
        outstandingRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if(index != -1){
                        Librarian librarian = (Librarian)CurrentSession.user;
                        librarian.sendOutstandingRequest(documents.get(index));
                        String message = "Done!\n";
                        JOptionPane.showMessageDialog(null, message, "SUCCESS", JOptionPane.PLAIN_MESSAGE);

                }
            }
        });
        menuWindow.setVisible(true);
    }
}