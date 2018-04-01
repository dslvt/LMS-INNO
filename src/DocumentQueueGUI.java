import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DocumentQueueGUI extends JFrame {
    private JTable table;
    private JScrollPane listScroller;

    public DocumentQueueGUI() {
        Document document = CurrentSession.editDocument;
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 300, 200);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Queue");
        menuWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());

        //ArrayList<User> users =
        Object[][] usersQueue = new Object[3][3];
        for (int i = 0; i < 3; ++i){
            usersQueue[i][0] = "Name";
            usersQueue[i][1] = "Phone Number";
            usersQueue[i][2] = "Type";
        }

        String []columnNames = {"Name","Phone Number","Type"};
        table = new JTable(usersQueue, columnNames);
        listScroller = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        listScroller.setPreferredSize(new Dimension(300, 200));
        containerM.add(listScroller);
        menuWindow.setVisible(true);
    }
}
