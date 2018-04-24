import com.mysql.jdbc.log.Log;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

class LibrarianGUI extends JFrame{
    private JButton Books = new JButton("Books");
    private JButton Users = new JButton("Users");
    private JButton Tasks = new JButton("Tasks");
    private JButton Request = new JButton("Checked out documents");
    private JButton LastActions = new JButton("Last actions");
    private JButton logOut = new JButton("Log out");
    private IntTextField intTextField;

    public LibrarianGUI(int user_id){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 250);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Librarian");
        menuWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuWindow.dispose();
                System.exit(0);
            }
        });
        Container containerM = menuWindow.getContentPane();
        if(Database.isAdmin(user_id))
            containerM.setLayout(new GridLayout(6, 1, 2, 2));
        else
           containerM.setLayout(new GridLayout(5, 1, 2, 2));
        containerM.add(Books);
        containerM.add(Users);
        containerM.add(Tasks);
        containerM.add(Request);
        if(Database.isAdmin(user_id)){
            Panel panel = new Panel(new GridLayout(1, 2, 2, 2));
            intTextField = new IntTextField(10, 3);
            panel.add(intTextField);
            panel.add(LastActions);
            containerM.add(panel);
        }
        containerM.add(logOut);

        Books.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                LibrarianDocumentGUI books = new LibrarianDocumentGUI(user_id);
            }
        });

        Users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianUserOptionsGUI users = new LibrarianUserOptionsGUI(user_id);
            }
        });

        Tasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianTasksGUI tasks = new LibrarianTasksGUI();
            }
        });

        Request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibrarianRequestGUI request = new LibrarianRequestGUI();
            }
        });

        LastActions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = intTextField.getValue();
                if(number == 0) {
                    String message = "Enter a number!\n";
                    JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    LastActionsGUI lastActionsGUI = new LastActionsGUI(number);
                }
            }
        });

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CurrentSession.user = null;
                CurrentSession.editUser = null;
                CurrentSession.editDocument = null;
                CurrentSession.setDate = 0L;
                menuWindow.dispose();
                FirstWindow restartFirstWindow = new FirstWindow();
                restartFirstWindow.setVisible(true);
            }
        });

        menuWindow.setVisible(true);
    }
}
class IntTextField extends JTextField {
    public IntTextField(int defval, int size) {
        super("" + defval, size);
    }

    protected Document createDefaultModel() {
        return new IntTextDocument();
    }

    public boolean isValid() {
        try {
            Integer.parseInt(getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getValue() {
        try {
            return Integer.parseInt(getText());
        } catch (Exception e) {
            return 0;
        }
    }
    class IntTextDocument extends PlainDocument {
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null)
                return;
            String oldString = getText(0, getLength());
            String newString = oldString.substring(0, offs) + str
                    + oldString.substring(offs);
            try {
                Integer.parseInt(newString + "0");
                super.insertString(offs, str, a);
            } catch (Exception e) {
            }
        }
    }
}