import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.xml.crypto.Data;

public class FirstWindow extends JFrame{
    private JButton buttonSingIn =  new JButton("Sign in");
    private JButton buttonSignUp =  new JButton("Sign up");
    private JLabel labelPhoneNumber =  new JLabel("Phone Number");
    private JTextField textFieldPhoneNumber =  new JTextField("", 5);
    private JLabel labelPassword =  new JLabel("Password");
    private JPasswordField fieldPassword = new JPasswordField();

    /**
     * creating first window GUI
     */
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
                if(Database.isCorrectAuthorization(textFieldPhoneNumber.getText(), fieldPassword.getText())){
                    int user_id = -1;
                    try {
                        ResultSet rs = Database.SelectFromDB("SELECT id FROM users WHERE phoneNumber =" + textFieldPhoneNumber.getText());
                        rs.next();
                        user_id = rs.getInt(1);
                    }catch (Exception h){
                        System.out.println("Error in FirstWindow: "+h.toString());
                    }
                    closeFirstWindow();
                    if(Database.isLibrarian(user_id)){
                        CurrentSession.user = Database.getLibrarianByNumber(textFieldPhoneNumber.getText());
                        LibrarianGUI librarianGUI = new LibrarianGUI(user_id);
                    }
                    else{
                        CurrentSession.user = Database.getPatronByNumber(textFieldPhoneNumber.getText());
                        MenuWindow menuWindow = new MenuWindow();
                    }

                    CurrentSession.CurrentWork();

                }
                else {
                String message = "User does not exist\n" + "You need to Sign Up";
                JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        this.getRootPane().setDefaultButton(buttonSingIn);
        container.add(buttonSingIn);
        buttonSignUp.addActionListener(new RegisterWindow());
        container.add(buttonSignUp);
    }
    public void closeFirstWindow() {this.setVisible(false);}
}
