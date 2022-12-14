import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Client {
    public Socket clientSocket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    private Thread receiveThread;
    private AppDesign appDesign;

    public Client(AppDesign appDesign) {
        this.clientSocket = new Socket();
        this.appDesign = appDesign;
    }

    public void Connect(String ip, int port) {
        if (this.clientSocket.isConnected()) {
            Close();
        }
        try {
            this.clientSocket = new Socket(ip, port);
            JOptionPane.showMessageDialog(null, "Kết nối đến server thành công", null, JOptionPane.INFORMATION_MESSAGE);
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối đến server", null, JOptionPane.ERROR_MESSAGE);
        }
        receiveThread = new Thread() {
            public void run() {
                Response();
            }
        };
        receiveThread.start();
    }

    public void Response() {
        while (IsConnected()) {
            String cmd = ReceiveCommand();
            if (cmd.equalsIgnoreCase("ViewKey")) {
                String text = (String) ReceiveData();
                appDesign.keyStroke.ApplyText(text);
            } else if (cmd.equalsIgnoreCase("CaptureScreen")) {
                BufferedImage bi = ScreenCapture.ConvertString((String) ReceiveData());
                appDesign.screenCapture.SetImage(new ImageIcon(bi));
            } else if (cmd.equalsIgnoreCase("Processes")) {
                TaskObject task = (TaskObject) ReceiveData();
                appDesign.processesController.SetProcess(task);
            } else if (cmd.equalsIgnoreCase("Apps")) {
                String[][] apps = (String[][]) ReceiveData();
                appDesign.appController.SetApps(apps);
            }
        }
    }

    public boolean IsConnected() {
        if (this.clientSocket == null)
            return false;
        return this.clientSocket.isConnected();
    }

    public void Close() {
        try {
            this.clientSocket.close();
        } catch (SocketException e) {
            this.clientSocket = null;
        } catch (EOFException e) {
            this.clientSocket = null;
            this.dataInputStream = null;
            this.dataOutputStream = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReceiveCommand() {
        if (!IsConnected()) {
            return "None";
        }
        String cmd = "None";
        try {
            cmd = dataInputStream.readUTF();
            if (cmd != null) {
                System.out.println(cmd);
                return cmd;
            }
        } catch (SocketException e) {
            this.clientSocket = null;
            this.dataInputStream = null;
            this.dataOutputStream = null;
            return "";
        } catch (EOFException e) {
            this.clientSocket = null;
            this.dataInputStream = null;
            this.dataOutputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cmd;
    }

    public Object ReceiveData() {
        if (!IsConnected()) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối dến server2");
            return null;
        }
        try {
            Object obj = null;
            ObjectInputStream objectInputStream;
            objectInputStream = new ObjectInputStream(dataInputStream);
            obj = objectInputStream.readObject();
            return obj;
        } catch (SocketException e) {
            this.clientSocket = null;
            this.dataInputStream = null;
            this.dataOutputStream = null;
            return "";
        } catch (EOFException e) {
            this.clientSocket = null;
            this.dataInputStream = null;
            this.dataOutputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void SendCommand(String cmd) {
        if (!IsConnected()) {
            JOptionPane.showMessageDialog(null, "Chưa kết nối dến server", null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            dataOutputStream.writeUTF(cmd);
            dataOutputStream.flush();
        } catch (SocketException e) {
            this.clientSocket = null;
        } catch (EOFException e) {
            this.clientSocket = null;
            this.dataInputStream = null;
            this.dataOutputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendData(String cmd, Object obj) {
        if (!IsConnected()) {
            JOptionPane.showMessageDialog(null, "Chưa kết nối dến server", null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            dataOutputStream.writeUTF(cmd);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
            objectOutputStream.writeObject(obj);
            dataOutputStream.flush();
        } catch (SocketException e) {
            this.clientSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
