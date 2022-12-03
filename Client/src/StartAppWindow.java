import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class StartAppWindow extends JFrame {
    private Client client;
    private JTextField textField = new JTextField();
    private JButton button = new JButton("<html><center>" + "Start" + "</center></html>");

    public StartAppWindow(
            Client client) {
        this.client = client;
        setLayout(null);
        setResizable(false);
        setSize(300, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        textField.setBounds(10, 10, 200, 20);
        button.setBounds(220, 10, 60, 20);

        add(textField);
        add(button);
        AddAction();
    }

    public void Open() {
        setVisible(true);
    }

    private void AddAction() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendData("StartApp", textField.getText());
            }
        });
    }
}
