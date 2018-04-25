import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * gui using to edit different type of documents
 */
public class EditDocumentGUI  extends JFrame {
    private JButton editingBook = new JButton("Edit Book");

    /**
     * window's constructor
     * @param user_id used to refresh window
     */
    public EditDocumentGUI(int user_id) {
        try {
            //set window size
            JFrame deleteBook = new JFrame();
            deleteBook.setBounds(100, 100, 250, 200);
            deleteBook.setLocationRelativeTo(null);
            deleteBook.setResizable(false);
            deleteBook.setTitle("Edit Book");
            deleteBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Container containerTB = deleteBook.getContentPane();
            containerTB.setLayout(new BorderLayout());

            //get all documents and parse it
            ArrayList<Document> documents = Database.getAllDocuments();
            Object[][] books = new Object[documents.size()][];
            for (int i = 0; i < documents.size(); i++) {
                books[i] = new Object[4];
                books[i][0] = documents.get(i).name;
                books[i][1] = documents.get(i).authors;
                books[i][2] = documents.get(i).location;
                books[i][3] = documents.get(i).price;
            }

            //set all documents info into table
            String[] columnNames = {"Name", "Authors", "Location", "Price"};
            JTable table = new JTable(books, columnNames);
            JScrollPane listScroller = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            listScroller.setPreferredSize(new Dimension(100,100));
            containerTB.add(listScroller, BorderLayout.CENTER);

            //edit button, find document and edit it
            editingBook.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();

                    //get correct document type
                    if(documents.get(index).type == DocumentType.book){
                        AddBookGUI book = new AddBookGUI(user_id);
                    }
                    else if (documents.get(index).type == DocumentType.journal){
                        AddJournalGUI journal = new AddJournalGUI(user_id);
                    }
                    else if (documents.get(index).type == DocumentType.av_material){
                        AddAVmaterialGUI AVmaterial = new AddAVmaterialGUI(user_id);
                    }
                }
            });

            //set button size
            editingBook.setPreferredSize(new Dimension(250, 40));
            containerTB.add(editingBook, BorderLayout.SOUTH);
            deleteBook.setVisible(true);
        }
        catch (Exception e){
            System.out.println("Error in DeleteBook " + e.toString());
        }
    }

}