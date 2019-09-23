package dataServerPre;

/**
 * Presentation layer facade of data server
 * provide single control point of data server GUI
 */
public class DataServerGUIFacade {
    /** private singleton instance */
    private DataServerGUIFacade instance = null;

    /**
     * Private constructor
     */
    private DataServerGUIFacade() {}

    /**
     * get the singleton instance
     * @return singleton instance of DataServerGUIFacade
     */
    public DataServerGUIFacade getInstance() {
        if (instance == null)
            instance = new DataServerGUIFacade();

        return instance;
    }

    /**
     * start run data server GUI, display frame
     */
    public void runDataServerGUI() {
        /* NEED FIX */
    }

    /**
     * go to data server configuration page
     */
    public void dataServerConfig() {
        /* NEED FIX */
    }

    /**
     * go to data server information page
     */
    public void dataServerInfo() {
        /* NEED FIX */
    }
}
