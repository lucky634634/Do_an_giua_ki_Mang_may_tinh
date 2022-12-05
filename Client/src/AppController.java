import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;

public class AppController extends JFrame implements MouseListener {
    private Client client;
    private String[] columnPanes = { "Name", "ID" };
    private JTable table = new JTable(new String[0][1], columnPanes);
    private JScrollPane scrollPane = new JScrollPane(table);
    private JButton butRefresh = new JButton("<html><center>" + "Refresh" + "</center></html>");
    private JButton butKill = new JButton("<html><center>" + "Kill" + "</center></html>");
    private JButton butStart = new JButton("<html><center>" + "Start" + "</center></html>");
    private StartAppWindow startAppWindow;

    private JButton[] buttons = { butRefresh, butKill, butStart };
    private static int frameWidth = 450;
    private static int frameHeight = 450;
    private static String title = "listApp";

    public AppController(Client client) {
        setLocation(160, 90);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        scrollPane.setBounds(30, 90, 390, 310);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        butRefresh.setBounds(30, 15, 80, 60);
        butKill.setBounds(130, 15, 80, 60);
        butStart.setBounds(230, 15, 80, 60);
        
        JButton tmpBut = new JButton();
        tmpBut.setBounds(330, 15, 90, 60);
        add(tmpBut);

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

    private void CenterAlignTitle() {
        this.setFont(new Font("System", Font.PLAIN, 14));
        Font f = this.getFont();
        java.awt.FontMetrics fm = this.getFontMetrics(f);
        int x = fm.stringWidth(title);
        int y = fm.stringWidth(" ");
        int z = (frameWidth/2) - (x/2);
        int w = z/y;
        String pad =" ";
        pad = String.format("%"+w+"s", pad);
        setTitle(pad+title);
    }

    private void PrepareGUI(JButton button) {
        button.setOpaque(true);
        button.setFont(new Font("System", Font.PLAIN, 12));
        button.setFocusable(false);
        button.setBackground(Color.decode("#E6E6FA"));//E8E8E8
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
