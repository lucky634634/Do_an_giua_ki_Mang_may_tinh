import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class KeyStroke extends JFrame {
    private JButton butHook = new JButton("<html><center>" + "Hook" + "</center></html>");
    private JButton butUnhook = new JButton("<html><center>" + "Unhook" + "</center></html>");
    private JButton butSave = new JButton("<html><center>" + "Lưu file" + "</center></html>");
    private JButton butDel = new JButton("<html><center>" + "Xóa" + "</center></html>");
    private static JTextArea textArea = new JTextArea();
    private Client client;

    public KeyStroke(Client client) {
        setSize(800, 450);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.client = client;
    }

    void Open() {
        PrepareGUI();
        AddAction();
        setVisible(true);
    }

    void PrepareGUI() {
        textArea.setEditable(false);
        textArea.setBackground(new Color(0, 0, 0));
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 760, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollBar sb = scrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
        butHook.setBounds(10, 320, 80, 80);
        butUnhook.setBounds(100, 320, 80, 80);
        butDel.setBounds(190, 320, 80, 80);
        add(scrollPane);
        add(butHook);
        add(butUnhook);
        add(butDel);
    }

    void AddAction() {
        butHook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("StartHook");
            }
        });
        butUnhook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("StopHook");
            }
        });
    }

    public static void ApplyText(String text) {
        textArea.setText(text);
    }
}
