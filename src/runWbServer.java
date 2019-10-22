import wbServerPre.wbServerViewControllers.WbServerGUIController;

public class runWbServer {
    public static void main(String[] args) {
        // log setting
        System.setProperty("my.log", "resources/log/wbServer.log");
        // security settings
        System.setProperty("java.security.policy","file:./security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        WbServerGUIController.getInstance().runWbServerGUI();
    }
}
