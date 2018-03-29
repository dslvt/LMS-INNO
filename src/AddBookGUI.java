import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AddBookGUI extends JFrame {
    Book book = (Book) CurrentSession.editDocument;
    private JLabel labelName = new JLabel("Name");
    private JTextField textFieldNameSU = new JTextField("", 5);

    private JLabel labelAuthor = new JLabel("Author");
    private JTextField textFieldAuthor = new JTextField("", 5);

    private JLabel labelPrice = new JLabel("Price");
    private JTextField textFieldPrice = new JTextField("", 5);

    private JLabel labelKeywords = new JLabel("Keywords");
    private JTextField textFieldKeywords = new JTextField("", 5);

    private JLabel labelPublisher = new JLabel("Publisher");
    private JTextField textFieldPublisher = new JTextField("", 5);

    private JLabel labelEdition = new JLabel("Edition");
    private JTextField textFieldEdition = new JTextField("",5);

    private JLabel labelPublishYear = new JLabel("Publish Year");
    private  JTextField textFieldPublishYear = new JTextField("", 5);

    private JLabel labelLocation = new JLabel("Location");
    private  JTextField textFieldLocation = new JTextField("", 5);

    private Checkbox Bestseller = new Checkbox("Bestseller");
    private Checkbox Reference = new Checkbox("Reference");
    private JButton add = new JButton("ADD");

    public AddBookGUI(){
        if(book != null) {
            Bestseller.setState(book.isBestseller);
            Reference.setState(book.isReference);
            textFieldNameSU.setText(book.name);
            textFieldAuthor.setText(book.authors.toString());
            textFieldPrice.setText(Integer.toString(book.price));
            textFieldKeywords.setText(book.keywords.toString());
            textFieldPublisher.setText(book.publisher);
            textFieldEdition.setText(book.edition);
            textFieldPublishYear.setText(Integer.toString(book.publishYear));
            textFieldLocation.setText(book.location);
        }

        JFrame BookWindow = new JFrame("Book");
        BookWindow.setBounds(100, 100, 250, 450);
        BookWindow.setLocationRelativeTo(null);
        BookWindow.setResizable(false);
        this.setTitle("Book");
        BookWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerSU = BookWindow.getContentPane();
        containerSU.setLayout(new GridLayout(10, 2, 2, 2));

        containerSU.add(labelName);
        containerSU.add(textFieldNameSU);
        containerSU.add(labelAuthor);
        containerSU.add(textFieldAuthor);
        containerSU.add(labelEdition);
        containerSU.add(textFieldEdition);
        containerSU.add(labelKeywords);
        containerSU.add(textFieldKeywords);
        containerSU.add(labelLocation);
        containerSU.add(textFieldLocation);
        containerSU.add(labelPrice);
        containerSU.add(textFieldPrice);
        containerSU.add(labelPublisher);
        containerSU.add(textFieldPublisher);
        containerSU.add(labelPublishYear);
        containerSU.add(textFieldPublishYear);
        containerSU.add(Bestseller);
        containerSU.add(Reference);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CurrentSession.editDocument!=null) {
                    book.ModifyInDB(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))), Integer.parseInt(textFieldPrice.getText()),
                            new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))), Reference.getState(), textFieldPublisher.getText(), textFieldEdition.getText(),
                            Integer.parseInt(textFieldPublishYear.getText()), Bestseller.getState(), textFieldLocation.getText(), CurrentSession.user.id);
                }else {
                    Book book = new Book(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))), Integer.parseInt(textFieldPrice.getText()),
                            new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))), Reference.getState(), textFieldPublisher.getText(), textFieldEdition.getText(),
                            Integer.parseInt(textFieldPublishYear.getText()), Bestseller.getState(), textFieldLocation.getText(), true);
                    book.CreateDocumentInDB(CurrentSession.user.id);
                }

                CurrentSession.editDocument = null;
                BookWindow.setVisible(false);
            }
        });
        containerSU.add(add);
        BookWindow.setVisible(true);
    }
}