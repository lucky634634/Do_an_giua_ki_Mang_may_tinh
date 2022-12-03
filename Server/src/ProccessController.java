import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ProccessController {
    public static TaskObject GetProccess() {
        Vector<String[]> taskList = new Vector<String[]>();
        ProcessBuilder builder = new ProcessBuilder("tasklist", "/fo", "csv");
        Process process;
        try {
            process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            boolean header = true;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (!header) {
                    taskList.add(new TaskProcess(line).toArray());
                } else {
                    header = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TaskObject(taskList);
    }

    public static String[] GetApps() {
        Vector<String> taskList = new Vector<String>();
        ProcessBuilder builder = new ProcessBuilder(
                "powershell \"gps | where {$_.MainWindowHandle -ne 0 } | Format-Table -HideTableHeaders  name\"");
        Process process;
        try {
            process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            boolean header = true;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (!header) {
                    taskList.add(line);
                } else {
                    header = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList.toArray(new String[0]);
    }
}
