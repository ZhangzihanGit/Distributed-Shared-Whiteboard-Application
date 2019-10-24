import wbServerApp.WbServerCmdValue;
import wbServerPre.wbServerViewControllers.WbServerGUIController;

import java.net.URISyntaxException;

public class runWbServer {
    public static void main(String[] args) throws URISyntaxException {
        // log setting
        System.setProperty("my.log", "resources/log/wbServer.log");
        // security settings
        System.setProperty("java.security.policy","file:./security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        WbServerCmdValue cmdValidator = new WbServerCmdValue(args);
        if (cmdValidator.isErrorFree()) {
            // rmi setting
            System.setProperty("java.rmi.server.hostname", cmdValidator.getServerIP());

            WbServerGUIController.getInstance().runWbServerGUI();
        }
    }
}
