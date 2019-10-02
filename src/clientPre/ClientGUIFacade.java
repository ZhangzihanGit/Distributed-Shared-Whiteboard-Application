package clientPre;

import clientApp.ClientAppFacade;
import org.apache.log4j.Logger;

/**
 * Presentation layer facade of client
 * provide single control point of client GUI
 */
public class ClientGUIFacade {
    private final static Logger logger = Logger.getLogger(ClientGUIFacade.class);

    /** private singleton instance */
    private static ClientGUIFacade instance = null;

    /**
     * Private constructor
     */
    private ClientGUIFacade() {}

    /**
     * get the singleton instance
     * @return singleton instance of ClientGUIControl
     */
    public static ClientGUIFacade getInstance() {
        if (instance == null)
            instance = new ClientGUIFacade();

        return instance;
    }

    /**
     * start run client GUI, display frame
     */
    public void runClientGUI() {
        /* NEED FIX */
        /* Test */
        ClientAppFacade.getInstance().connectWbServer("localhost");
        System.out.println(ClientAppFacade.getInstance().register("d", "e"));
        System.out.println(ClientAppFacade.getInstance().login("d", "e"));
    }

    /**
     * go to client login page
     */
    public void clientLogin() {
        /* NEED FIX */
    }

    /**
     * go to role(Manager or user) selection page
     */
    public void roleSelection() {
        /* NEED FIX */
    }

    /**
     * go to server selection page
     */
    public void serverSelection() {
        /* NEED FIX */
    }

    /**
     * go to manager whiteboard page
     */
    public void managerWB() {
        /* NEED FIX */
    }

    /**
     * go to user whiteboard page
     */
    public void userWB() {
        /* NEED FIX */
    }
}
