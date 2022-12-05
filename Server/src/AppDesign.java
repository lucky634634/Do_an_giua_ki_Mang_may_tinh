import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.MouseListener;

public class AppDesign extends JFrame implements MouseListener {
    JButton startButton = new JButton("<html><center>" +"Mở server"+ "</center></html>");
    Server server = new Server();

    private static int frameWidth = 300;
    private static int frameHeight= 300;
    private static String title = "Server";
    private boolean isOn = true;

    AppDesign() {
        setLocation(25, 25);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();
        
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void ShowButton() {
        startButton.setBounds(40, 25, 200, 200);
        PrepareGUI(startButton);
        add(startButton);
        pack();
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

    private void CenterAlignTitle() {
        this.setFont(new Font("System", Font.PLAIN, 20));
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
        button.setFont(new Font("System", Font.PLAIN, 14));
        button.setFocusable(false);
        button.setBackground(Color.decode("#F7CAC9"));//E8E8E8
        button.setBorder(new RoundedBorder(14));
        button.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isOn) {
            e.getComponent().setBackground(Color.decode("#C1C5D2"));// DCDEE6
            isOn = !isOn;
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
        if(isOn) {
            e.getComponent().setBackground(Color.decode("#EB7C79"));// DCDEE6
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(isOn) {
            e.getComponent().setBackground(Color.decode("#F7CAC9"));//E8E8E8
        }
    }

}
