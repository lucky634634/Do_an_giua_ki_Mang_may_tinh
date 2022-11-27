import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Server {
    public ServerSocket serverSocket;
    public Socket clientSocket;

    Server() {
        try {
            this.serverSocket = new ServerSocket();
            this.clientSocket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenServer(int port) {
        if (!this.serverSocket.isClosed())
            Close();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Close() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ShowIP() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void AcceptConnection() {
        try {
            this.clientSocket = this.serverSocket.accept();
            JOptionPane.showMessageDialog(null, "Kết nối thành công");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean IsConnected() {
        return this.clientSocket.isConnected();
    }

    public void CollectCommand()
    {
        
    }
}
