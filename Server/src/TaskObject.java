import java.io.Serializable;
import java.util.Vector;

public class TaskObject implements Serializable {
    String[][] taskList;

    public TaskObject() {
        taskList = null;
    }

    public TaskObject(Vector<String[]> taskList) {
        this.taskList = new String[taskList.size()][taskList.get(0).length];
        for (int i = 0; i < taskList.size(); i++) {
            this.taskList[i] = taskList.get(i);
        }
    }

    public String[][] GetList() {
        return this.taskList;
    }
}
