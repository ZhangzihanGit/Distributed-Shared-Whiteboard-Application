import clientPre.clientViewControllers.ClientGUIController;

/**
 * Run client program
 */
public class runClient {

    public static void main(String[] args) {
        // log setting
        long ms = System.currentTimeMillis();
        System.setProperty("my.log", "resources/log/client-" + ms + ".log");
        // security settings
        System.setProperty("java.security.policy","file:./security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ClientGUIController.getInstance().runClientGUI();
    }
}
