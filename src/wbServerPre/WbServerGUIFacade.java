package wbServerPre;

/**
 * Presentation layer facade of whiteboard server
 * provide single control point of whiteboard server GUI
 */
public class WbServerGUIFacade {
    /** private singleton instance */
    private WbServerGUIFacade instance = null;

    /**
     * Private constructor
     */
    private WbServerGUIFacade() {}

    /**
     * get the singleton instance
     * @return singleton instance of WbServerGUIFacade
     */
    public WbServerGUIFacade getInstance() {
        if (instance == null)
            instance = new WbServerGUIFacade();

        return instance;
    }

    /**
     * start run whiteboard server GUI, display frame
     */
    public void runWbServerGUI() {
        /* NEED FIX */
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
