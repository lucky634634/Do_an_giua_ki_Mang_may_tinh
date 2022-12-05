import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.MouseListener;

public class StartAppWindow extends JFrame implements MouseListener {
    private Client client;
    private JTextField textField = new JTextField("Nhập tên");
    private JButton button = new JButton("<html><center>" + "Start" + "</center></html>");

    private static int frameWidth = 350;
    private static int frameHeight = 90;
    private static String title = "Start";

    public StartAppWindow(Client client) {
        this.client = client;
        setLocation(200, 120);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();

        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        textField.setBounds(25, 15, 200, 20);
        textField.setFont(new Font("System", Font.PLAIN, 12));
        textField.setBackground(Color.decode("#E6E6FA"));
        add(textField);

        button.setBounds(240, 15, 80, 20);
        PrepareGUI(button);
        add(button);

        pack();
        AddAction();
    }

    public void Open() {
        textField.setText("Nhập tên");
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
