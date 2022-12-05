import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseListener;
import java.awt.Color;

public class ScreenCapture extends JFrame implements MouseListener {
    private ImageIcon imageIcon = new ImageIcon();
    private JLabel label = new JLabel();
    private JButton butCapture = new JButton("<html><center>" + "Chụp" + "</center></html>");
    private JButton butSave = new JButton("<html><center>" + "Lưu" + "</center></html>");
    private JScrollPane scrollPane;
    private Client client;

    private JButton[] buttons = { butCapture, butSave };
    private static int frameWidth = 960;// 1024
    private static int frameHeight = 520;
    private String title = "pic";


    public ScreenCapture(Client client) {
        setLocation(160, 90);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();

        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        scrollPane = new JScrollPane(label);
        scrollPane.setBounds(10, 10, 820, 470);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        butCapture.setBounds(840, 10, 80, 320);
        butSave.setBounds(840, 380, 80, 80);

        for (JButton button : buttons) {
            PrepareGUI(button);
            add(button);
        }

        pack();
        AddAction();
        this.client = client;
    }

    public void Open() {
        setVisible(true);
        client.SendCommand("CaptureScreen");
    }

    private void AddAction() {
        butCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("CaptureScreen");
            }
        });

        butSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SavePicture();
            }
        });
    }

    public void SetImage(ImageIcon icon) {
        imageIcon = icon;
        Image image = icon.getImage();
        if (icon.getIconWidth() > icon.getIconHeight()) {
            Image newImage = image.getScaledInstance(800, icon.getIconHeight() * 800 / icon.getIconWidth(),
                    Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(newImage));

        } else {
            Image newImage = image.getScaledInstance(icon.getIconWidth() * 450 / icon.getIconHeight(), 450,
                    Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(newImage));
        }
    }

    public static BufferedImage ConvertString(String data) {
        BufferedImage bi = null;
        byte[] imageBytes = Base64.getDecoder().decode(data);
        InputStream is = new ByteArrayInputStream(imageBytes);
        try {
            bi = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }

    public void SavePicture() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.png", ".png"));
        fileChooser.setSelectedFile(new File("Screen.png"));
        int value = fileChooser.showSaveDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getParent();
            String fileName = fileToSave.getName();
            if (!fileName.endsWith(".png")) {
                fileName += ".png";
                fileToSave = new File(filePath, fileName);
            }
            try {
                fileToSave.createNewFile();
                ImageIO.write((RenderedImage) imageIcon.getImage(), "png", fileToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        button.setFont(new Font("System", Font.PLAIN, 14));
        button.setFocusable(false);
        button.setBackground(Color.decode("#E6E6FA"));//E8E8E8
        button.setBorder(new RoundedBorder(10));
        button.addMouseListener(this);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        e.getComponent().setBackground(Color.decode("#B8C7F4"));// DCDEE6
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        e.getComponent().setBackground(Color.decode("#E6E6FA"));// DCDEE6
    }

}