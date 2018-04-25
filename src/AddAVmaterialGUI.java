import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;

public class AddAVmaterialGUI extends JFrame {
    AVmaterial material = (AVmaterial) CurrentSession.editDocument;
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
    private JButton add = new JButton("Accept");

    /**
     * init constuctor
     * @param user_id to refresh window
     */
    public AddAVmaterialGUI(int user_id){
        if(material != null) {
            Reference.setState(material.isReference);
            textFieldNameSU.setText(material.name);
            textFieldAuthor.setText(material.authors.toString());
            textFieldPrice.setText(Integer.toString(material.price));
            textFieldKeywords.setText(material.keywords.toString());
            textFieldLocation.setText(material.location);
        }
        JFrame AVmaterialWindow = new JFrame("AV material");
        AVmaterialWindow.setBounds(100, 100, 250, 450);
        AVmaterialWindow.setLocationRelativeTo(null);
        AVmaterialWindow.setResizable(false);
        this.setTitle("AV material");
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
                if(CurrentSession.editDocument != null){
                    //modifying avmaterial
                    material.ModifyInDB(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))), Integer.parseInt(textFieldPrice.getText()),
                            new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))), Reference.getState(),  textFieldLocation.getText(), CurrentSession.user.id);
                }else {

                    //creating new avmaterial and creating its in database
                    AVmaterial aVmaterial = new AVmaterial(textFieldNameSU.getText(), new ArrayList(Arrays.asList(textFieldAuthor.getText().split(" "))), Integer.parseInt(textFieldPrice.getText()),
                            new ArrayList(Arrays.asList(textFieldKeywords.getText().split(" "))), Reference.getState(), true, textFieldLocation.getText());
                    aVmaterial.CreateDocumentInDB(CurrentSession.user.id);
                }
                CurrentSession.editDocument = null;
                AVmaterialWindow.setVisible(false);

                LibrarianDocumentGUI restart = new LibrarianDocumentGUI(user_id);
            }
        });
        containerSU.add(add);
        AVmaterialWindow.setVisible(true);
    }
}