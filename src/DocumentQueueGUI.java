import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DocumentQueueGUI extends JFrame {
    private JTable table;
    private JScrollPane listScroller;

    public DocumentQueueGUI() {
        Document document = CurrentSession.editDocument;
        CurrentSession.editDocument = null;
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 300, 200);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Queue");
        menuWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());

        ArrayList<Patron> users = Database.getDocumentQueue(document);
        Object[][] usersQueue = new Object[users.size()][3];
        for (int i = 0; i < usersQueue.length; i++){
            usersQueue[i][0] = users.get(i).name;
            usersQueue[i][1] = users.get(i).phoneNumber;
            usersQueue[i][2] = users.get(i).type;
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
