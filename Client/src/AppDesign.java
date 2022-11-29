import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class AppDesign extends JFrame {
    private JTextField txtIP = new JTextField("Nhập IP");
    private JButton butConnect = new JButton("Kết nối");
    private JButton butProcess = new JButton(
            "<html><center>" + "Process running" + "</center></html>");
    private JButton butApp = new JButton(
            "<html><center>" + "App running" + "</center></html>");
    private JButton butShutdown = new JButton("<html><center>" + "Tắt máy" + "</center></html>");
    private JButton butLogout = new JButton("<html><center>" + "Đăng xuất" + "</center></html>");
    private JButton butExit = new JButton("<html><center>" + "Thoát" + "</center></html>");
    private JButton butPic = new JButton("<html><center>" + "Chụp màn hình" + "</center></html>");
    private JButton butKeyStroke = new JButton("<html><center>" + "Key stroke" + "</center></html>");

    private Client client = new Client();
    private KeyStroke keyStroke = new KeyStroke(client);
    private ProcessesController processesController = new ProcessesController();
    private ScreenCapture screenCapture = new ScreenCapture();

    AppDesign() {
        setBounds(0, 0, 400, 300);
        setVisible(true);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void ShowButton() {
        txtIP.setBounds(10, 10, 200, 20);
        butConnect.setBounds(220, 10, 100, 20);
        add(txtIP);
        add(butConnect);

        butProcess.setBounds(10, 40, 60, 210);
        butShutdown.setBounds(80, 40, 95, 60);
        butLogout.setBounds(185, 40, 95, 60);
        butApp.setBounds(80, 110, 200, 60);
        butPic.setBounds(80, 180, 200, 70);
        butKeyStroke.setBounds(290, 40, 80, 130);

        butExit.setBounds(290, 180, 80, 70);
        add(butProcess);
        add(butShutdown);
        add(butLogout);
        add(butApp);
        add(butPic);
        add(butKeyStroke);
        add(butExit);
        setVisible(true);
        AddAction();
    }

    private void AddAction() {
        butConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.Connect(txtIP.getText(), 5656);
            }
        });

        butKeyStroke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyStroke.Open();
            }

        });

        butProcess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processesController.Open();
            }
        });

        butExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.Close();
                System.exit(0);
            }
        });

        butPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenCapture.Open();
            }
        });

        txtIP.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtIP.getText().charAt(0) == 'N') {
                    txtIP.setText("");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        butShutdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("ShutDown");
                System.out.println(client.ReceiveCommand());
            }
        });

        butLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("LogOff");
                System.out.println(client.ReceiveCommand());
            }
        });
    }
}