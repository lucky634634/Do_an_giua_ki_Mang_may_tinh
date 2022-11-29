import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
    public Socket clientSocket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;

    public Client() {
        this.clientSocket = new Socket();
    }

    public void Connect(String ip, int port) {
        if (this.clientSocket.isConnected()) {
            Close();
        }
        try {
            this.clientSocket = new Socket(ip, port);
            JOptionPane.showMessageDialog(null, "Kết nối đến server thành công");
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối dến server");
        }
    }

    public boolean IsConnected() {
        return this.clientSocket.isConnected();
    }

    public void Close() {
        try {
            this.clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ReceiveCommand() {
        if (!IsConnected())
            return "None";
        String cmd = "None";
        try {
            cmd = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cmd;
    }

    public Object ReceiveData() {
        if (!IsConnected())
            return null;
        Object obj = null;
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(dataInputStream);
            obj = objectInputStream.readObject();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    public void SendCommand(String cmd) {
        try {
            dataOutputStream.writeUTF(cmd);
            dataOutputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
