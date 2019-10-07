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
     * start run server at localhost
     * @param port Port number, String
     */
    public void runWbServer(String port) {
        wbServer.runWbServer(port);
    }

    /**
     * connect to database server
     * @param ip IP address, String
     * @param port port, String
     * @return True if connect successfully, Boolean
     */
    public Boolean connectDbServer(String ip, String port) {
        return wbServer.connectDbServer(ip, port);
    }

    /**
     * Register new users
     * @param username Username, String
     * @param password Password, String
     * @return True if register successfully
     */
    public Boolean register(String username, String password) {
        return wbServer.register(username, password);
    }

    /**
     * Existing user login authentication
     * @param username Username, String
     * @param password Password, String
     * @return True if authenticate success, Boolean
     */
    public Boolean login(String username, String password) {
        return wbServer.login(username, password);
    }

    /**
     * exit server program
     */
    public void exit() {
        wbServer.exit();
        logger.info("User exit whiteboard server program");
        System.exit(1);
    }
}
