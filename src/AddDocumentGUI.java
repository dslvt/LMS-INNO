import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;

class AddDocumentGUI  extends JFrame{
    private JRadioButton Book = new JRadioButton("Book");
    private JRadioButton Article = new JRadioButton("Article");
    private JRadioButton AVmaterial = new JRadioButton("AV Material");
    private JRadioButton Journal = new JRadioButton("Journal");
    private JButton Choose = new JButton("Choose");

    private void groupButton(){
        ButtonGroup Common = new ButtonGroup();
        Common.add(Book);
        Common.add(Article);
        Common.add(AVmaterial);
        Common.add(Journal);
    }

    public AddDocumentGUI(int user_id) {
        JFrame AddBook = new JFrame();
        AddBook.setBounds(100, 100, 200, 150);
        AddBook.setLocationRelativeTo(null);
        AddBook.setResizable(false);
        AddBook.setTitle("Sign Up");
        AddBook.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerM = AddBook.getContentPane();
        containerM.setLayout(new GridLayout(3, 2, 2, 2));
        groupButton();
        containerM.add(Book);
        containerM.add(Article);
        containerM.add(Journal);
        containerM.add(AVmaterial);
        Choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Book.isSelected()){
                    AddBookGUI book = new AddBookGUI(user_id);
                }
                else if (Journal.isSelected()){
                    AddJournalGUI journal =  new AddJournalGUI(user_id);
                }
                else if (AVmaterial.isSelected()){
                    AddAVmaterialGUI Avmaterial = new AddAVmaterialGUI(user_id);
                }
                else if (Article.isSelected()){
                    AddArticleGUI article = new AddArticleGUI();
                }
            }
        });
        Choose.setPreferredSize(new Dimension(250, 40));
        containerM.add(Choose, BorderLayout.SOUTH);
        AddBook.setVisible(true);
    }
}