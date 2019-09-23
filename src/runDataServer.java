import dataServerPre.DataServerGUIFacade;

public class runDataServer {
    public static void main(String[] args) {
        System.setProperty("my.log", "resources/log/dataServer.log");

        DataServerGUIFacade.getInstance().runDataServerGUI();
    }
}
