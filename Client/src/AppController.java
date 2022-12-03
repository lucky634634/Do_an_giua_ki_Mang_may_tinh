import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AppController extends JFrame {
    private Client client;
    private String[] columnPanes = { "Name", "ID" };
    private JTable table = new JTable(new String[0][1], columnPanes);
    private JScrollPane scrollPane = new JScrollPane(table);
    private JButton butRefresh = new JButton("<html><center>" + "Refresh" + "</center></html>");
    private JButton butKill = new JButton("<html><center>" + "Kill" + "</center></html>");
    private JButton butStart = new JButton("<html><center>" + "Start" + "</center></html>");
    private StartAppWindow startAppWindow;

    public AppController(Client client) {
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(600, 450);
        scrollPane.setBounds(10, 10, 570, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        butRefresh.setBounds(10, 330, 60, 60);
        butKill.setBounds(80, 330, 60, 60);
        butStart.setBounds(150, 330, 60, 60);
        add(scrollPane);
        add(butRefresh);
        add(butKill);
        add(butStart);
        AddAction();
        this.client = client;
        this.startAppWindow = new StartAppWindow(client);
    }

    public void Open() {
        setVisible(true);
        client.SendCommand("Apps");
    }

    private void AddAction() {
        butRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("Apps");
            }
        });
        butKill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String task = table.getModel().getValueAt(row, 1).toString();
                client.SendData("Kill", task);
            }
        });
        butStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAppWindow.Open();
            }
        });
    }

    public void SetApps(String[][] apps) {
        this.table = new JTable(apps, columnPanes);
        this.scrollPane.setViewportView(table);
    }
}
