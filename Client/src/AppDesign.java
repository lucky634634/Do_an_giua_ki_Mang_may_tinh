import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class AppDesign extends JFrame implements MouseListener {
    private JTextField txtIP = new JTextField("Nhập IP");
    private JButton butConnect = new JButton("<html><center>" +"Kết nối"+ "</center></html>");
    private JButton butProcess = new JButton(
            "<html><center>" + "Process Running" + "</center></html>");
    private JButton butApp = new JButton(
            "<html><center>" + "App Running" + "</center></html>");
    private JButton butShutdown = new JButton("<html><center>" + "Tắt máy" + "</center></html>");
    private JButton butLogout = new JButton("<html><center>" + "Đăng xuất" + "</center></html>");
    private JButton butExit = new JButton("<html><center>" + "Thoát" + "</center></html>");
    private JButton butPic = new JButton("<html><center>" + "Chụp màn hình" + "</center></html>");
    private JButton butKeyStroke = new JButton("<html><center>" + "Keystroke" + "</center></html>");

    private JButton[] buttons = { butConnect, butProcess, butApp, butShutdown,
            butLogout, butExit, butPic, butKeyStroke };
    private static int frameWidth = 400;
    private static int frameHeight = 300;
    private static String title = "Client";

    public Client client = new Client(this);
    public KeyStroke keyStroke = new KeyStroke(client);
    public ProcessesController processesController = new ProcessesController(client);
    public ScreenCapture screenCapture = new ScreenCapture(client);
    public AppController appController = new AppController(client);

    AppDesign() {
        setLocation(0, 0);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();
        setVisible(true);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void ShowButton() {
        txtIP.setBounds(10, 10, 270, 20);
        butConnect.setBounds(290, 10, 80, 20);
        butProcess.setBounds(10, 40, 60, 210);
        butShutdown.setBounds(80, 40, 95, 60);
        butLogout.setBounds(185, 40, 95, 60);
        butApp.setBounds(80, 110, 200, 60);
        butPic.setBounds(80, 180, 200, 70);
        butKeyStroke.setBounds(290, 40, 80, 130);
        butExit.setBounds(290, 180, 80, 70);

        txtIP.setFont(new Font("System", Font.PLAIN, 12));
        txtIP.setBackground(Color.decode("#E6E6FA"));
        add(txtIP);

        for (JButton button : buttons) {
            PrepareGUI(button);
            add(button);
        }

        pack();
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

        butApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appController.Open();
            }
        });

        butExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        butPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenCapture.Open();
            }
        });

        txtIP.addMouseListener(this);

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

    private void CenterAlignTitle() {
        this.setFont(new Font("System", Font.PLAIN, 14));
        Font f = this.getFont();
        FontMetrics fm = this.getFontMetrics(f);
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