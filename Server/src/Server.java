import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Server {
    public ServerSocket serverSocket;
    public Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

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
        while (true) {
            try {
                this.clientSocket = this.serverSocket.accept();
                JOptionPane.showMessageDialog(null, "Kết nối thành công");
                this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
                String cmdString = ReceiveCommand();
                if (cmdString.equalsIgnoreCase("ShutDown")) {
                    System.out.println(cmdString);
                    SendCommand("Accept");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean IsConnected() {
        return this.clientSocket.isConnected();
    }

    public String ReceiveCommand() {
        if (!IsConnected()) {
            return "None";
        }
        try {
            String cmd = dataInputStream.readUTF();
            if (cmd != null) {
                return cmd;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "None";
    }

    public Object ReceiveData() {
        Object obj;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(dataInputStream);
            obj = objectInputStream.readObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void SendData(String cmd, Object obj) {
        try {
            dataOutputStream.writeUTF(cmd);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
            objectOutputStream.writeObject(obj);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
