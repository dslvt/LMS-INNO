import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class RegisterWindow extends JFrame implements ActionListener {
    private JLabel labelNameSU = new JLabel("Name");
    private JTextField textFieldNameSU = new JTextField("", 5);
    private JLabel labelPhoneNumberSU = new JLabel("Phone Number");
    private JTextField textFieldPhoneNumberSU = new JTextField("", 5);
    private JLabel labelPasswordSU = new JLabel("Password");
    private JPasswordField fieldPasswordSU = new JPasswordField("", 5);
    private JLabel labelAdressSU = new JLabel("Adress");
    private JTextField textFieldAdressSU = new JTextField("", 5);
    private JRadioButton radioStudent = new JRadioButton("Student");
    private JRadioButton radioFacultyMember = new JRadioButton("Faculty Member");
    private JButton submit = new JButton("Submit");

    /**
     * creating register GUI
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        JFrame signUp = new JFrame("Sign Up");
        signUp.setBounds(100, 100, 250, 300);
        signUp.setLocationRelativeTo(null);
        signUp.setResizable(false);
        this.setTitle("Sign Up");
        signUp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerSU = signUp.getContentPane();
        containerSU.setLayout(new GridLayout(6, 2, 2, 2));
        containerSU.add(labelNameSU);
        containerSU.add(textFieldNameSU);
        containerSU.add(labelPhoneNumberSU);
        containerSU.add(textFieldPhoneNumberSU);
        containerSU.add(labelPasswordSU);
        containerSU.add(fieldPasswordSU);
        containerSU.add(labelAdressSU);
        containerSU.add(textFieldAdressSU);
        ButtonGroup groupIsFacultyMember = new ButtonGroup();
        groupIsFacultyMember.add(radioStudent);
        groupIsFacultyMember.add(radioFacultyMember);
        containerSU.add(radioStudent);
        radioStudent.setSelected(true);
        containerSU.add(radioFacultyMember);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nado ispravit'
                Patron patron = new Patron(textFieldNameSU.getText(), fieldPasswordSU.getText(), textFieldPhoneNumberSU.getText(), textFieldAdressSU.getText(), radioFacultyMember.isSelected(), 0, PatronType.student);
                patron.CreateUserDB();
                signUp.setVisible(false);
            }
        });
        containerSU.add(submit);
        signUp.setVisible(true);
    }
}