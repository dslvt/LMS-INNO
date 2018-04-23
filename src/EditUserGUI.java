import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserGUI extends JFrame{
    private JLabel labelNameSU = new JLabel("Name");
    private JTextField textFieldNameSU = new JTextField(CurrentSession.editUser.name, 5);
    private JLabel labelPhoneNumberSU = new JLabel("Phone Number");
    private JTextField textFieldPhoneNumberSU = new JTextField(CurrentSession.editUser.phoneNumber, 5);
    private JLabel labelPasswordSU = new JLabel("Password");
    private JPasswordField fieldPasswordSU = new JPasswordField(CurrentSession.editUser.password, 5);
    private JLabel labelAdressSU = new JLabel("Adress");
    private JTextField textFieldAdressSU = new JTextField(CurrentSession.editUser.password, 5);
    private JRadioButton radioStudent = new JRadioButton("Student");
    private JRadioButton radioFacultyMember = new JRadioButton("Faculty Member");
    private JButton submit = new JButton("Edit");


    public EditUserGUI(int user_id){
        JFrame signUp = new JFrame("Edit User");
        signUp.setBounds(100, 100, 250, 300);
        signUp.setLocationRelativeTo(null);
        signUp.setResizable(false);
        this.setTitle("Edit User");
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
        if(CurrentSession.editUser.isFacultyMember){
            radioFacultyMember.doClick();
        }else{
            radioStudent.doClick();
        }

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentSession.editUser.phoneNumber = textFieldPhoneNumberSU.getText();
                CurrentSession.editUser.name = textFieldNameSU.getText();
                CurrentSession.editUser.password = fieldPasswordSU.getText();
                CurrentSession.editUser.address = fieldPasswordSU.getText();
                if(radioFacultyMember.isSelected()){
                    CurrentSession.editUser.isFacultyMember = true;
                }else{
                    CurrentSession.editUser.isFacultyMember = false;
                }
                signUp.setVisible(false);
                LibrarianUserOptionsGUI librarianUserOptionsGUI = new LibrarianUserOptionsGUI(user_id);
            }
        });
        containerSU.add(submit);
        signUp.setVisible(true);
    }
}