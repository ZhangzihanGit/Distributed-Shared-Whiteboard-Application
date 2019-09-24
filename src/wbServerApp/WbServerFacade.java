package wbServerApp;

import org.apache.log4j.Logger;

public class WbServerFacade {
    private final static Logger logger = Logger.getLogger(WbServerFacade.class);

    /** private singleton instance */
    private static WbServerFacade instance = null;

    private WbServerApplication wbServer = null;

    /**
     * Private constructor
     */
    private WbServerFacade() {
        wbServer = new WbServerApplication();
    }

    /**
     * get the singleton instance
     * @return singleton instance of WbServerFacade
     */
    public static WbServerFacade getInstance() {
        if (instance == null)
            instance = new WbServerFacade();

        return instance;
    }

    /**
     * start run server
     */
    public void runWbServer() {
        wbServer.runWbServer();
    }

    /**
     * connect to database server
     * @param ip
     * @return True if connect successfully
     */
    public boolean connectDbServer(String ip) {
        return wbServer.connectDbServer(ip);
    }

    /**
     * exit server program
     */
    public void exit() {
        wbServer.exit();
        logger.info("User exit whiteboard server program");
        System.exit(1);
    }

    /**
     * Set up server address (ip)
     * @param ip
     * @return true if set successfully
     */
    public boolean setAddress(String ip) {
        return wbServer.setAddress(ip);
    }
}
