import wbServerPre.wbServerViewControllers.WbServerGUIController;

public class runWbServer {
    public static void main(String[] args) {
        System.setProperty("my.log", "resources/log/wbServer.log");

        WbServerGUIController.getInstance().runWbServerGUI();
    }
}
