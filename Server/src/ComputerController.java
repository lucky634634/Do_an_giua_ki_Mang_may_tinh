import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ComputerController {
    public static void ShutDown() {
        try {
            ProcessBuilder builder = new ProcessBuilder("shutdown", "/s", "/t", "5");
            Process process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void LogOut() {
        try {
            ProcessBuilder builder = new ProcessBuilder("shutdown", "/l");
            Process process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
