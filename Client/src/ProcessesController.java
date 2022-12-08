import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseListener;
import java.awt.Color;

public class ProcessesController extends JFrame implements MouseListener {
    String[] columnPane = { "Name", "PID", "SessionName", "Session", "memUsage" };

    private JTable table = new JTable(new String[0][5], columnPane);
    private JScrollPane scrollPane = new JScrollPane(table);
    private Client client;
    private JButton butRefresh = new JButton("<html><center>" + "Refresh" + "</center></html>");
    private JButton butKill = new JButton("<html><center>" + "Kill" + "</center></html>");
    private JButton butStart = new JButton("<html><center>" + "Start" + "</center></html>");
    private JButton butDelete = new JButton("<html><center>" + "Delete" + "</center></html>");
    private StartAppWindow startAppWindow;

    private JButton[] buttons = { butRefresh, butKill, butStart, butDelete };
    private static int frameWidth = 540;
    private static int frameHeight = 450;
    private static String title = "process";

    public ProcessesController(Client client) {
        // setSize(600, 450);
        setLocation(160, 90);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();

        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        scrollPane.setBounds(30, 90, 475, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        // 30 151 272 393
        butRefresh.setBounds(30, 15, 100, 60);
        butKill.setBounds(151, 15, 100, 60);
        butStart.setBounds(272, 15, 100, 60);

        butDelete.setBounds(393, 15, 110, 60);

        for (JButton button : buttons) {
            PrepareGUI(button);
            add(button);
        }

        pack();
        AddAction();
        this.client = client;
        this.startAppWindow = new StartAppWindow(client);
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

        butDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table = new JTable(new String[0][5], columnPane);
                scrollPane.setViewportView(table);
            }
        });
    }

    public void SetProcess(TaskObject taskObject) {
        this.table = new JTable(taskObject.GetList(), columnPane);
        this.scrollPane.setViewportView(table);
    }

    private void CenterAlignTitle() {
        this.setFont(new Font("System", Font.PLAIN, 14));
        Font f = this.getFont();
        java.awt.FontMetrics fm = this.getFontMetrics(f);
        int x = fm.stringWidth(title);
        int y = fm.stringWidth(" ");
        int z = (frameWidth / 2) - (x / 2);
        int w = z / y;
        String pad = " ";
        pad = String.format("%" + w + "s", pad);
        setTitle(pad + title);
    }

    private void PrepareGUI(JButton button) {
        button.setOpaque(true);
        button.setFont(new Font("System", Font.PLAIN, 14));
        button.setFocusable(false);
        button.setBackground(Color.decode("#E6E6FA"));// E8E8E8
        button.setBorder(new RoundedBorder(10));
        button.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().setBackground(Color.decode("#B8C7F4"));// DCDEE6
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.getComponent().setBackground(Color.decode("#E6E6FA"));// DCDEE6
    }

}
