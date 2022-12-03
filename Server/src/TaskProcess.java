public class TaskProcess {
    String imageName;
    String pid;
    String sessionName;
    String session;
    String memUsage;

    public TaskProcess() {
        imageName = "";
        pid = "";
        sessionName = "";
        session = "";
        memUsage = "";
    }

    public TaskProcess(String string) {
        Convert(string);
    }

    public void Convert(String string) {
        String[] str = string.split(",");
        imageName = str[0].split("\"")[1];
        pid = str[1].split("\"")[1];
        sessionName = str[2].split("\"")[1];
        session = str[3].split("\"")[1];
        memUsage = str[4].split("\"")[1];
    }

    public String[] toArray() {
        String arr[] = { imageName, pid, sessionName, session, memUsage };
        return arr;
    }
}
