import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;

class AddArticleGUI extends JFrame {
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
    private  JTextField textFieldLocation = new JTextField("", 5);
    private Checkbox Bestseller = new Checkbox("Bestseller");
    private Checkbox Reference = new Checkbox("Reference");

    private JButton add = new JButton("Accept");

    public AddArticleGUI(){
        JFrame ArticleWindow = new JFrame("Book");
        ArticleWindow.setBounds(100, 100, 250, 450);
        ArticleWindow.setLocationRelativeTo(null);
        ArticleWindow.setResizable(false);
        this.setTitle("Article");
        ArticleWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerSU = ArticleWindow.getContentPane();
        containerSU.setLayout(new GridLayout(9, 2, 2, 2));

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
        containerSU.add(Bestseller);
        containerSU.add(Reference);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentSession.editDocument = null;
                ArticleWindow.setVisible(false);
            }
        });
        containerSU.add(add);
        ArticleWindow.setVisible(true);
    }
}