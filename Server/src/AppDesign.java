import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class AppDesign extends JFrame {
    JButton startButton = new JButton("Mở server");
    Server server = new Server();
    KeyLogger keyLogger;

    AppDesign() {
        setSize(400, 300);
        setLayout(null);

        setTitle("Server");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void ShowButton() {
        startButton.setBounds(100, 25, 200, 200);
        add(startButton);
        AddAction();
    }

    private void AddAction() {
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server.OpenServer(5656);
                System.out.println("IP: " + server.ShowIP());
                startButton.setEnabled(false);
                Thread thread = new Thread() {
                    public void run() {
                        server.AcceptConnection();
                    }
                };
                thread.start();
            }
        });
    }
}
