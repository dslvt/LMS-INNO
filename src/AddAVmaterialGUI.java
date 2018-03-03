import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;

public class AddAVmaterialGUI extends JFrame {
    private JLabel labelName = new JLabel("Name");
    private JTextField textFieldNameSU = new JTextField("", 5);

    private JLabel labelAuthor = new JLabel("Author");
    private JTextField textFieldAuthor = new JTextField("", 5);

    private JLabel labelPrice = new JLabel("Price");
    private JTextField textFieldPrice = new JTextField("", 5);

    private JLabel labelKeywords = new JLabel("Keywords");
    private JTextField textFieldKeywords = new JTextField("", 5);

    private JLabel labelLocation = new JLabel("Location");
    private JTextField textFieldLocation = new JTextField("", 5);

    private Checkbox Reference = new Checkbox("Reference");

    private JButton add = new JButton("ADD");

    public AddAVmaterialGUI(){
        JFrame AVmaterialWindow = new JFrame("Book");
        AVmaterialWindow.setBounds(100, 100, 250, 450);
        AVmaterialWindow.setLocationRelativeTo(null);
        AVmaterialWindow.setResizable(false);
        this.setTitle("Book");
        AVmaterialWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerSU = AVmaterialWindow.getContentPane();
        containerSU.setLayout(new GridLayout(6, 2, 2, 2));

        containerSU.add(labelName);
        containerSU.add(textFieldNameSU);
        containerSU.add(labelAuthor);
        containerSU.add(textFieldAuthor);
        containerSU.add(labelKeywords);
        containerSU.add(textFieldKeywords);
        containerSU.add(labelPrice);
        containerSU.add(textFieldPrice);
        containerSU.add(labelLocation);
        containerSU.add(textFieldLocation);
        containerSU.add(Reference);


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AVmaterial aVmaterial = new AVmaterial(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))), Integer.parseInt(textFieldPrice.getText()),
                    new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))), Reference.getState(), true, textFieldLocation.getText());

                aVmaterial.CreateDocumentInDB(CurrentSession.user.id);
                AVmaterialWindow.setVisible(false);
            }
        });
        containerSU.add(add);
        AVmaterialWindow.setVisible(true);
    }
}