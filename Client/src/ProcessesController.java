import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ProcessesController extends JFrame {
    String[] columnPane = { "Name", "PID", "SessionName", "Session", "memUsage" };

    private JTable table = new JTable(new String[0][5], columnPane);
    private JScrollPane scrollPane = new JScrollPane(table);
    private Client client;
    private JButton butRefresh = new JButton("<html><center>" + "Refresh" + "</center></html>");
    private JButton butKill = new JButton("<html><center>" + "Kill" + "</center></html>");

    public ProcessesController(Client client) {
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(600, 450);
        scrollPane.setBounds(10, 10, 570, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        butRefresh.setBounds(10, 330, 60, 60);
        butKill.setBounds(80, 330, 60, 60);
        add(scrollPane);
        add(butRefresh);
        add(butKill);
        this.client = client;
        AddAction();
    }

    public void Open() {
        setVisible(true);
        client.SendCommand("Processes");
    }

    private void AddAction() {
        butRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("Processes");
            }
        });

        butKill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String task = table.getModel().getValueAt(row, 0).toString();
                client.SendData("Kill", task);
            }
        });
    }

    public void SetProcess(TaskObject taskObject) {
        this.table = new JTable(taskObject.GetList(), columnPane);
        this.scrollPane.setViewportView(table);
    }
}
