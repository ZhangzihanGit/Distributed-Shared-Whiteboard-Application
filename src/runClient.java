import clientPre.ClientGUIFacade;

/**
 * Run client program
 */
public class runClient {
    public static void main(String[] args) {
        long ms = System.currentTimeMillis();

        System.setProperty("my.log", "resources/log/client-" + ms + ".log");

        ClientGUIFacade.getInstance().runClientGUI();
    }
}
