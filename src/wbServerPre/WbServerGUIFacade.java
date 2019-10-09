package wbServerPre;

import org.apache.log4j.Logger;
import wbServerApp.WbServerFacade;

/**
 * Presentation layer facade of whiteboard server
 * provide single control point of whiteboard server GUI
 */
public class WbServerGUIFacade {
    private final static Logger logger = Logger.getLogger(WbServerGUIFacade.class);

    /** private singleton instance */
    private static WbServerGUIFacade instance = null;

    /**
     * Private constructor
     */
    private WbServerGUIFacade() {}

    /**
     * get the singleton instance
     * @return singleton instance of WbServerGUIFacade
     */
    public static WbServerGUIFacade getInstance() {
        if (instance == null)
            instance = new WbServerGUIFacade();

        return instance;
    }

    /**
     * start run whiteboard server GUI, display frame
     */
    public void runWbServerGUI() {
        WbServerFacade.getInstance().connectDbServer("localhost", "1099");
        WbServerFacade.getInstance().runWbServer("1111");
    }

    /**
     * go to whiteboard server configuration page
     */
    public void wbServerConfig() {
        /* NEED FIX */
    }

    /**
     * go to whiteboard server information page
     */
    public void wbServerInfo() {
        /* NEED FIX */
    }
}
