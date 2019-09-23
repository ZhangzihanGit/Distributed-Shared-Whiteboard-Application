import wbServerPre.WbServerGUIFacade;

public class runWbServer {
    public static void main(String[] args) {
        System.setProperty("my.log", "resources/log/wbServer.log");

        WbServerGUIFacade.getInstance().runWbServerGUI();
    }
}
