import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FirstWindow extends JFrame{
    private JButton buttonSingIn =  new JButton("Sign in");
    private JButton buttonSignUp =  new JButton("Sign up");
    private JLabel labelPhoneNumber =  new JLabel("Phone Number");
    private JTextField textFieldPhoneNumber =  new JTextField("", 5);
    private JLabel labelPassword =  new JLabel("Password");
    private JPasswordField fieldPassword = new JPasswordField();

    public FirstWindow(){
        super("Simple Example");
        this.setBounds(100, 100, 250, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ZaiLib");

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 2,2));
        container.add(labelPhoneNumber);
        container.add(textFieldPhoneNumber);
        container.add(labelPassword);
        container.add(fieldPassword);
        buttonSingIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database database = new Database();
                if(database.isCorrectAuthorization(textFieldPhoneNumber.getText(), fieldPassword.getText())){
                    closeFirstWindow();
                    MenuWindow menuWindow = new MenuWindow();
                }
                else {
                String message = "User does not exist\n" + "You need to Sign Up";
                JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        container.add(buttonSingIn);
        buttonSignUp.addActionListener(new RegisterWindow());
        container.add(buttonSignUp);
    }
    public void closeFirstWindow() {this.setVisible(false);}
}