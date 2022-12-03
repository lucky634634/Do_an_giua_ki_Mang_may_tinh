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

    public static String[][] GetApps() {
        Vector<String[]> taskList = new Vector<String[]>();
        ProcessBuilder builder = new ProcessBuilder(
                "powershell", "\"gps | where {$_.MainWindowHandle -ne 0 } | Format-Table -HideTableHeaders name, ID\"");
        Process process;
        try {
            process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (!line.equalsIgnoreCase("")) {
                    String[] task = line.split(" ");
                    String[] fin = { "", "" };
                    Boolean name = true;
                    for (int i = 0; i < task.length; i++) {
                        if (!task[i].equalsIgnoreCase("")) {
                            if (name) {
                                fin[0] += task[i];
                                name = false;
                            } else {
                                fin[1] += task[i];
                            }
                        } else {
                            name = false;
                        }
                    }
                    taskList.add(fin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList.toArray(new String[taskList.size()][0]);
    }

    public static void Kill(String task) {
        Vector<String[]> taskList = new Vector<String[]>();
        ProcessBuilder builder = new ProcessBuilder(
                "taskkill", "/f", "/t", "/im", task);
        Process process;
        try {
            process = builder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
