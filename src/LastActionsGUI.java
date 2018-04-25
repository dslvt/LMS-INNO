import com.mysql.jdbc.log.Log;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LastActionsGUI extends JFrame {
    private Logging logging = new Logging();
    private JTable table;

    public LastActionsGUI(int number){
        JFrame menuWindow = new JFrame();
        menuWindow.setBounds(100, 100, 250, 250);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);
        menuWindow.setTitle("Last actions");
        menuWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Container containerM = menuWindow.getContentPane();
        containerM.setLayout(new FlowLayout());
        ArrayList<String> lastActions = Logging.getLast(number);
        Object[][] obj = new Object[lastActions.size()][1];
        for (int i=0; i< lastActions.size(); i++)
            obj[i][0] = lastActions.get(i);
        Object[] str = {"Actions"};
        table = new JTable(obj, str);
        JScrollPane listScroller = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        listScroller.setPreferredSize(new Dimension(245, 245));
        containerM.add(listScroller);
        menuWindow.setVisible(true);
    }
}
