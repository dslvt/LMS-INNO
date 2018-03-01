import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;

public class AddBookGUI extends JFrame {
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
                BookWindow.setVisible(false);
            }
        });
        containerSU.add(add);
        BookWindow.setVisible(true);
    }
}