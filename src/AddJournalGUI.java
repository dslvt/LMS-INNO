import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;

public class AddJournalGUI extends JFrame {
    private JLabel labelName = new JLabel("Name");
    private JTextField textFieldNameSU = new JTextField("", 5);

    private JLabel labelAuthor = new JLabel("Author");
    private JTextField textFieldAuthor = new JTextField("", 5);

    private JLabel labelPrice = new JLabel("Price");
    private JTextField textFieldPrice = new JTextField("", 5);

    private JLabel labelKeywords = new JLabel("Keywords");
    private JTextField textFieldKeywords = new JTextField("", 5);

    private JLabel labelPublishYear = new JLabel("Publish Year");
    private  JTextField textFieldPublishYear = new JTextField("", 5);

    private JLabel labelIssue = new JLabel("Issue");
    private JTextField textFieldIssue = new JTextField("", 5);

    private JLabel labelLocation = new JLabel("Location");
    private JTextField textFieldLocation = new JTextField("", 5);

    private JLabel labelEditor = new JLabel("Editor");
    private JTextField textFieldEditor = new JTextField("", 5);

    private Checkbox Reference = new Checkbox("Reference");

    private JButton add = new JButton("ADD");

    public AddJournalGUI(){
        Journal journal = (Journal) CurrentSession.editDocument;
        if(journal != null) {
            Reference.setState(journal.isReference);
            textFieldNameSU.setText(journal.name);
            textFieldAuthor.setText(journal.authors.toString());
            textFieldPrice.setText(Integer.toString(journal.price));
            textFieldKeywords.setText(journal.keywords.toString());
            textFieldLocation.setText(journal.location);
            textFieldPublishYear.setText(journal.publicationDate);
            textFieldIssue.setText(journal.issue);
            textFieldEditor.setText(journal.editor);
        }
        JFrame JournalWindow = new JFrame("Journal");
        JournalWindow.setBounds(100, 100, 250, 450);
        JournalWindow.setLocationRelativeTo(null);
        JournalWindow.setResizable(false);
        this.setTitle("Book");
        JournalWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerSU = JournalWindow.getContentPane();
        containerSU.setLayout(new GridLayout(10, 2, 2, 2));

        containerSU.add(labelName);
        containerSU.add(textFieldNameSU);
        containerSU.add(labelAuthor);
        containerSU.add(textFieldAuthor);
        containerSU.add(labelKeywords);
        containerSU.add(textFieldKeywords);
        containerSU.add(labelLocation);
        containerSU.add(textFieldLocation);
        containerSU.add(labelPrice);
        containerSU.add(textFieldPrice);
        containerSU.add(labelPublishYear);
        containerSU.add(textFieldPublishYear);
        containerSU.add(labelIssue);
        containerSU.add(textFieldIssue);
        containerSU.add(labelEditor);
        containerSU.add(textFieldEditor);
        containerSU.add(Reference);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(journal != null){
                    journal.ModifyInDB(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))),
                            Integer.parseInt(textFieldPrice.getText()), new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))),
                            Reference.getState(), textFieldPublishYear.getText(), textFieldIssue.getText(), textFieldEditor.getText(),  textFieldLocation.getText(), CurrentSession.user.id);
                }else {
                    Journal journal = new Journal(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))),
                            Integer.parseInt(textFieldPrice.getText()), new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))),
                            Reference.getState(), textFieldPublishYear.getText(), textFieldIssue.getText(), textFieldEditor.getText(), true, textFieldLocation.getText());
                }

                journal.CreateDocumentInDB(CurrentSession.user.id);

                CurrentSession.editDocument = null;
                JournalWindow.setVisible(false);
            }
        });
        containerSU.add(add);
        JournalWindow.setVisible(true);
    }
}